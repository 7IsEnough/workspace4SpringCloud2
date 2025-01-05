package com.itheima.publisher;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.CorrelationData.Confirm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author promise
 * @date 2024/6/29 - 18:19
 */
@SpringBootTest
@Slf4j
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

  @Test
  public void testSendObject() {


    Map<String, Object> msg = Maps.newHashMap("name", "Jack");
    msg.put("age", 21);



    rabbitTemplate.convertAndSend("object.queue", msg);
  }

  @Test
  public void testConfirmCallback() throws InterruptedException {

    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

    correlationData.getFuture().addCallback(new ListenableFutureCallback<Confirm>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("spring amqp 处理确认结果异常", ex);
      }

      @Override
      public void onSuccess(Confirm result) {
        // 判断是否成功
        if (result.isAck()) {
          log.info("收到ConfirmCallback ack，消息发送成功!");
        } else {
          log.info("收到ConfirmCallback nack，消息发送失败! reason {}", result.getReason());
        }
      }
    });

    // 交换机名
    String exchangeName = "hmall.direct";

    // String message = "红色";
    // String message = "蓝色";
    String message = "黄色";



    rabbitTemplate.convertAndSend(exchangeName,"yellow22", message, correlationData);

    Thread.sleep(2000);
  }

  @Test
  public void testSendMessage() {
    // 1. 自定义构建消息
    Message message = MessageBuilder.withBody(
        "Hello,Spring amqp".getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
    // 2. 构建消息
    for (int i = 0; i < 1000000; i++) {
      rabbitTemplate.convertAndSend("simple.queue", message);
    }

  }

  @Test
  public void testSendLazyMessage() {
    // 1. 自定义构建消息
    Message message = MessageBuilder.withBody(
        "Hello,Spring amqp".getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();
    // 2. 构建消息
    for (int i = 0; i < 1000000; i++) {
      rabbitTemplate.convertAndSend("lazy.queue", message);
    }

  }

  @Test
  public void testSendDelayMessage() {
    rabbitTemplate.convertAndSend("normal.direct","hi", "hello, delay", message -> {
      message.getMessageProperties().setExpiration("10000");
      return message;
    });

  }

  @Test
  public void testSendDelayMessageByPlugin() {
    rabbitTemplate.convertAndSend("delay.direct","hi", "hello, delay2", message -> {
      message.getMessageProperties().setDelay(10000);
      return message;
    });

  }
}
