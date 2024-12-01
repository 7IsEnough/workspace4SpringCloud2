package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author promise
 * @date 2024/6/22 - 0:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "hm.cart")
public class CartProperties {

  private Integer maxItems;
}
