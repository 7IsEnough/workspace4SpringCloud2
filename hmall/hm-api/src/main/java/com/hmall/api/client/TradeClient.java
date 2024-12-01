package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author promise
 * @date 2024/6/21 - 0:21
 */
@FeignClient("trade-service")
public interface TradeClient {

  @PutMapping("/orders/{orderId}")
  public void markOrderPaySuccess(@PathVariable("orderId") long orderId);
}
