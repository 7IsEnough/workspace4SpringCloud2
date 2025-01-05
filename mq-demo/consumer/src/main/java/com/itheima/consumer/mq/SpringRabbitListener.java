package com.itheima.consumer.mq;

import java.time.LocalTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

/**
 * @author promise
 * @date 2024/6/29 - 18:24
 */
@Component
@Slf4j
public class SpringRabbitListener {

  @RabbitListener(queues = "simple.queue")
  public void listenSimpleQueue(Message message) {
    log.info("监听到simple.queue的消息：id {}", message.getMessageProperties().getMessageId());
    log.info("监听到simple.queue的消息：{}", new String(message.getBody()));
    throw new RuntimeException("我是故意的");
  }

  @RabbitListener(queues = "work.queue")
  public void listenWorkQueue1(String message) throws InterruptedException {
    System.out.println("消费者1接收到消息：" + message + ", " + LocalTime.now());
    Thread.sleep(25);
  }

  @RabbitListener(queues = "work.queue")
  public void listenWorkQueue2(String message) throws InterruptedException {
    System.out.println("消费者2接收到消息：" + message + ", " + LocalTime.now());
    Thread.sleep(200);
  }

  @RabbitListener(queues = "fanout.queue1")
  public void listenFanoutQueue1(String message) {
    log.info("消费者1监听到fanout.queue1的消息：{}", message);
  }

  @RabbitListener(queues = "fanout.queue2")
  public void listenFanoutQueue2(String message) {
    log.info("消费者2监听到fanout.queue2的消息：{}", message);
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(name = "direct.queue1", durable = "true"),
      exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
      key = {"red", "blue"}
  ))
  public void listenDirectQueue1(String message) {
    log.info("消费者1监听到direct.queue1的消息：{}", message);
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(name = "direct.queue2", durable = "true"),
      exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
      key = {"red", "yellow"}
  ))
  public void listenDirectQueue2(String message) {
    log.info("消费者2监听到direct.queue2的消息：{}", message);
  }

  @RabbitListener(queues = "topic.queue1")
  public void listenTopicQueue1(String message) {
    log.info("消费者1监听到topic.queue1的消息：{}", message);
  }

  @RabbitListener(queues = "topic.queue2")
  public void listenTopicQueue2(String message) {
    log.info("消费者2监听到topic.queue2的消息：{}", message);
  }

  @RabbitListener(queues = "object.queue")
  public void listenObjectQueue(Map<String, Object> message) {
    log.info("消费者1监听到topic.queue1的消息：{}", message);
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(name = "dlx.queue", durable = "true"),
      exchange = @Exchange(name = "dlx.direct", type = ExchangeTypes.DIRECT),
      key = {"hi"}
  ))
  public void listenDlxQueue(String message) {
    log.info("消费者监听到dlx.queue的消息：{}", message);
  }


  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(name = "delay.queue", durable = "true"),
      exchange = @Exchange(name = "delay.direct", delayed = "true"),
      key = {"hi"}
  ))
  public void listenDelayQueue(String message) {
    log.info("消费者监听到delay.queue的消息：{}", message);
  }
}
