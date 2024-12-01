package com.hmall.gateway.filters;

import com.hmall.gateway.filters.PrintAnyGatewayFilterFactory.Config;
import java.util.List;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author promise
 * @date 2024/6/21 - 17:46
 */
@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

  @Override
  public GatewayFilter apply(Config config) {
    //    return new GatewayFilter() {
    //      @Override
    //      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    //        System.out.println("print any filter running");
    //        return chain.filter(exchange);
    //      }
    //    };
    return new OrderedGatewayFilter(
        new GatewayFilter() {
          @Override
          public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            System.out.println("print any filter running");
            String a = config.getA();
            String b = config.getB();
            String c = config.getC();
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("c = " + c);
            return chain.filter(exchange);
          }
        },
        1);
  }


  @Data
  public static class Config {
    private String a;
    private String b;
    private String c;
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return List.of("a", "b", "c");
  }

  public PrintAnyGatewayFilterFactory() {
    super(Config.class);
  }
}
