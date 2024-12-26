package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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
public class FanoutConfiguration {


  @Bean
  public FanoutExchange fanoutExchange() {
    // return new FanoutExchange("hmall.fanout");
    return ExchangeBuilder.fanoutExchange("hmall.fanout").build();
  }

  @Bean
  public Queue fanoutQueue1() {
    // return new Queue("fanout.queue1");
    return QueueBuilder.durable("fanout.queue1").build();
  }

  @Bean
  public Binding fanoutQueue1Binding(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
  }

  @Bean
  public Queue fanoutQueue2() {
    // return new Queue("fanout.queue2");
    return QueueBuilder.durable("fanout.queue2").build();
  }

  @Bean
  public Binding fanoutQueue2Binding(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
  }
}
