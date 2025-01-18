package com.hmall.item.es;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.item.domain.po.Item;
import com.hmall.item.domain.po.ItemDoc;
import com.hmall.item.service.IItemService;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author promise
 * @date 2025/1/12 - 18:44
 */
@SpringBootTest(properties = {"spring.profiles.active=local"})
public class ElasticDocumentTest {

  private RestHighLevelClient client;

  @Autowired
  private IItemService itemService;
  

  @Test
  public void testConnection() {
    System.out.println("client = " + client);
  }

  @Test
  public void testIndexDoc() throws IOException {
    // 0.准备文档数据
    // 根据id查询数据库
    Item item = itemService.getById(1L);
    // 把数据库数据转为ES文档数据
    ItemDoc itemDoc = BeanUtil.copyProperties(item, ItemDoc.class);
    itemDoc.setPrice(29900);
    // 1.准备Request
    IndexRequest request = new IndexRequest("items").id(itemDoc.getId());
    // 2.准备请求参数
    request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON);
    // 3.发送请求
    client.index(request, RequestOptions.DEFAULT);
  }

  @Test
  public void testGetDoc() throws IOException {
    // 1.准备Request
    GetRequest request = new GetRequest("items", "1");
    // 2.发送请求
    GetResponse response = client.get(request, RequestOptions.DEFAULT);
    // 3.解析响应结果
    String json = response.getSourceAsString();
    ItemDoc itemDoc = JSONUtil.toBean(json, ItemDoc.class);
    System.out.println("itemDoc = " + itemDoc);
  }

  @Test
  public void testDeleteDoc() throws IOException {
    // 1.准备Request
    DeleteRequest request = new DeleteRequest("items", "1");
    // 2.发送请求
    client.delete(request, RequestOptions.DEFAULT);
  }

  @Test
  public void testUpdateDoc() throws IOException {
    // 1.准备Request
    UpdateRequest request = new UpdateRequest("items", "1");
    // 2.准备请求参数
    request.doc(
        "price", 25600
    );
    // 3.发送请求
    client.update(request, RequestOptions.DEFAULT);
  }

  @Test
  public void testBulkDoc() throws IOException {
    int pageNo = 1, pageSize = 500;
    while (true) {
      // 0.准备文档数据
      List<Item> records = itemService.lambdaQuery().eq(Item::getStatus, 1)
          .page(new Page<>(pageNo, pageSize)).getRecords();
      if (CollectionUtil.isEmpty(records)) {
        return;
      }

      // 1.准备Request
      BulkRequest request = new BulkRequest();
      // 2.准备请求参数
      for (Item item : records) {
        request.add(new IndexRequest("items").id(item.getId().toString()).source(JSONUtil.toJsonStr(BeanUtil.copyProperties(item, ItemDoc.class)), XContentType.JSON));
      }

      // 3.发送请求
      client.bulk(request, RequestOptions.DEFAULT);

      // 4.翻页
      pageNo++;
    }
  }

  @BeforeEach
  public void setUp() {
    client = new RestHighLevelClient(RestClient.builder(
        HttpHost.create("192.168.47.100:9200")
    ));
  }

  @AfterEach
  public void tearDown() throws Exception {
    if (client != null) {
      client.close();
    }
  }
}
