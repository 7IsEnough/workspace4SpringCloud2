package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author promise
 * @date 2024/12/27 - 1:19
 */
@Configuration
public class DirectConfiguration {


  @Bean
  public DirectExchange directExchange() {
    // return new DirectExchange("hmall.direct");
    return ExchangeBuilder.directExchange("hmall.direct").build();
  }

  @Bean
  public Queue directQueue1() {
    // return new Queue("direct.queue1");
    return QueueBuilder.durable("direct.queue1").build();
  }

  @Bean
  public Binding directQueue1BindingRed(Queue directQueue1, DirectExchange directExchange) {
    return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
  }

  @Bean
  public Binding directQueue1BindingBlue(Queue directQueue1, DirectExchange directExchange) {
    return BindingBuilder.bind(directQueue1).to(directExchange).with("blue");
  }

  @Bean
  public Queue directQueue2() {
    // return new Queue("direct.queue1");
    return QueueBuilder.durable("direct.queue2").build();
  }

  @Bean
  public Binding directQueue2BindingRed(Queue directQueue2, DirectExchange directExchange) {
    return BindingBuilder.bind(directQueue2).to(directExchange).with("red");
  }

  @Bean
  public Binding directQueue2BindingYellow(Queue directQueue2, DirectExchange directExchange) {
    return BindingBuilder.bind(directQueue2).to(directExchange).with("yellow");
  }
}
