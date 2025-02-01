package com.hmall.cart;

import com.hmall.api.config.DefaultFeignConfig;
import com.hmall.cart.config.LoadBalancerConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author promise
 * @date 2024/6/19 - 16:48
 */
@MapperScan("com.hmall.cart.mapper")
@LoadBalancerClients(defaultConfiguration = LoadBalancerConfiguration.class)
@EnableFeignClients(basePackages = "com.hmall.api.client", defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
public class CartApplication {
  public static void main(String[] args){
    SpringApplication.run(CartApplication.class, args);
  }

}
