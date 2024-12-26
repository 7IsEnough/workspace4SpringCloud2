package com.itheima.publisher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author promise
 * @date 2024/6/29 - 18:19
 */
@SpringBootTest
class SpringAmqpTest {


  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void testSimpleQueue() {

    String queueName = "simple.queue";

    String message = "Hello,Spring amqp";



    rabbitTemplate.convertAndSend(queueName, message);
  }


  @Test
  public void testWorkQueue() {

    String queueName = "work.queue";



    for (int i = 0; i < 50; i++) {
      String message = "Hello,Spring amqp_" + i;

      rabbitTemplate.convertAndSend(queueName, message);
    }





  }


  @Test
  public void testFanoutQueue() {

    // 交换机名
    String exchangeName = "hmall.fanout";

    String message = "Hello,everyone";



    rabbitTemplate.convertAndSend(exchangeName,null, message);
  }

  @Test
  public void testDirectQueue() {

    // 交换机名
    String exchangeName = "hmall.direct";

    // String message = "红色";
    // String message = "蓝色";
    String message = "黄色";



    rabbitTemplate.convertAndSend(exchangeName,"yellow", message);
  }

  @Test
  public void testTopicQueue() {

    // 交换机名
    String exchangeName = "hmall.topic";

    // String message = "红色";
    // String message = "蓝色";
    String message = "好冷";



    rabbitTemplate.convertAndSend(exchangeName,"china.weather", message);
  }
}
