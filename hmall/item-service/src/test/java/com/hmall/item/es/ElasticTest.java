package com.hmall.item.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author promise
 * @date 2025/1/12 - 18:44
 */
public class ElasticTest {

  private RestHighLevelClient client;

  @Test
  public void testConnection() {
    System.out.println("client = " + client);
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
