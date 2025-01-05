package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author promise
 * @date 2024/12/27 - 1:19
 */
@Configuration
public class NormalConfiguration {


  @Bean
  public DirectExchange normalExchange() {
    // return new DirectExchange("hmall.direct");
    return ExchangeBuilder.directExchange("normal.direct").build();
  }

  @Bean
  public Queue normalQueue() {
    // return new Queue("direct.queue1");
    return QueueBuilder.durable("normal.queue").deadLetterExchange("dlx.direct").build();
  }

  @Bean
  public Binding normalExchangeBinding(Queue normalQueue, DirectExchange normalExchange) {
    return BindingBuilder.bind(normalQueue).to(normalExchange).with("hi");
  }

}
