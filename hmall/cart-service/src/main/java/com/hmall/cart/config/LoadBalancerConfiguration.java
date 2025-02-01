package com.hmall.cart.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @author promise
 * @date 2025/2/1 - 23:53
 */
public class LoadBalancerConfiguration {


  @Bean
  public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
      Environment environment, NacosDiscoveryProperties nacosDiscoveryProperties, LoadBalancerClientFactory loadBalancerClientFactory) {
    String name = environment.getProperty("loadbalancer.client.name");
    return new NacosLoadBalancer(
        loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name, nacosDiscoveryProperties);
  }
}
