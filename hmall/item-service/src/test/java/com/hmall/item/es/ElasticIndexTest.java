package com.hmall.item.es;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author promise
 * @date 2025/1/12 - 18:44
 */
public class ElasticIndexTest {

  private RestHighLevelClient client;

  private final String MAPPING_TEMPLATE = "{\n"
      + "  \"mappings\": {\n"
      + "    \"properties\": {\n"
      + "      \"id\": {\n"
      + "        \"type\": \"keyword\"\n"
      + "      },\n"
      + "      \"name\":{\n"
      + "        \"type\": \"text\",\n"
      + "        \"analyzer\": \"ik_max_word\"\n"
      + "      },\n"
      + "      \"price\":{\n"
      + "        \"type\": \"integer\"\n"
      + "      },\n"
      + "      \"stock\":{\n"
      + "        \"type\": \"integer\"\n"
      + "      },\n"
      + "      \"image\":{\n"
      + "        \"type\": \"keyword\",\n"
      + "        \"index\": false\n"
      + "      },\n"
      + "      \"category\":{\n"
      + "        \"type\": \"keyword\"\n"
      + "      },\n"
      + "      \"brand\":{\n"
      + "        \"type\": \"keyword\"\n"
      + "      },\n"
      + "      \"sold\":{\n"
      + "        \"type\": \"integer\"\n"
      + "      },\n"
      + "      \"commentCount\":{\n"
      + "        \"type\": \"integer\",\n"
      + "        \"index\": false\n"
      + "      },\n"
      + "      \"isAD\":{\n"
      + "        \"type\": \"boolean\"\n"
      + "      },\n"
      + "      \"updateTime\":{\n"
      + "        \"type\": \"date\"\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  @Test
  public void testConnection() {
    System.out.println("client = " + client);
  }

  @Test
  public void testCreateIndex() throws IOException {
    // 1.准备Request对象
    CreateIndexRequest request = new CreateIndexRequest("items");
    // 2.准备请求参数
    request.source(MAPPING_TEMPLATE, XContentType.JSON);
    // 3.发送请求
    client.indices().create(request, RequestOptions.DEFAULT);
  }

  @Test
  public void testGetIndex() throws IOException {
    // 1.准备Request对象
    GetIndexRequest request = new GetIndexRequest("items");
    // 2.发送请求
    boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
    System.out.println("exists = " + exists);
  }

  @Test
  public void testDeleteIndex() throws IOException {
    // 1.准备Request对象
    DeleteIndexRequest request = new DeleteIndexRequest("items");
    // 2.发送请求
    client.indices().delete(request, RequestOptions.DEFAULT);
    
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
