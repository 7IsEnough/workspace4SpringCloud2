package com.itheima.consumer.mq;

import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author promise
 * @date 2024/6/29 - 18:24
 */
@Component
@Slf4j
public class SpringRabbitListener {

  @RabbitListener(queues = "simple.queue")
  public void listenSimpleQueue(String message) {
    log.info("监听到simple.queue的消息：{}", message);
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
}
