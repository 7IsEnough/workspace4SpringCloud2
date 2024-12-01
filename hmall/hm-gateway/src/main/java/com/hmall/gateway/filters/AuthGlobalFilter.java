package com.hmall.gateway.filters;

import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author promise
 * @date 2024/6/21 - 18:13
 */
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

  private final AuthProperties authProperties;

  private final JwtTool jwtTool;

  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (isExclude(request.getPath().toString())) {
      return chain.filter(exchange);
    }

    String token = null;
    List<String> headers = request.getHeaders().get("authorization");
    if (!CollectionUtils.isEmpty(headers)) {
      token = headers.get(0);
    }

    Long userId;
    try {
      userId = jwtTool.parseToken(token);
    } catch (Exception e) {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      return response.setComplete();
    }

    ServerWebExchange serverWebExchange = exchange.mutate()
        .request(builder -> builder.header("user-info", userId.toString())).build();

    return chain.filter(serverWebExchange);
  }

  private boolean isExclude(String path) {
    List<String> excludePaths = authProperties.getExcludePaths();
    return excludePaths.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
