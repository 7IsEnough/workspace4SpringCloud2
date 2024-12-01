package com.hmall.api.client;

import com.hmall.api.client.fallback.ItemClientFallbackFactory;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author promise
 * @date 2024/6/20 - 22:02
 */
@FeignClient(value = "item-service", fallbackFactory = ItemClientFallbackFactory.class)
public interface ItemClient {

  @GetMapping("/items")
  List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

  @PutMapping("/items/stock/deduct")
  public void deductStock(@RequestBody List<OrderDetailDTO> items);
}
