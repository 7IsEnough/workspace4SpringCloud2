package com.hmall.api.config;

import com.hmall.api.client.fallback.ItemClientFallbackFactory;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.Logger.Level;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Objects;
import org.springframework.context.annotation.Bean;

/**
 * @author promise
 * @date 2024/6/20 - 22:54
 */
public class DefaultFeignConfig {

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }


  @Bean
  public RequestInterceptor userInfoRequestInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate requestTemplate) {
        if (Objects.nonNull(UserContext.getUser())) {
          requestTemplate.header("user-info", UserContext.getUser().toString());
        }
      }
    };
  }

  @Bean
  public ItemClientFallbackFactory itemClientFallbackFactory() {
    return new ItemClientFallbackFactory();
  }
}
