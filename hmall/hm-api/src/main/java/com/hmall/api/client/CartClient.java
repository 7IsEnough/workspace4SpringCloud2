package com.hmall.api.client;

import java.util.Collection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author promise
 * @date 2024/6/20 - 23:55
 */
@FeignClient("cart-service")
public interface CartClient {

  @DeleteMapping("/carts")
  public void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);
}
