package com.hmall.api.client.fallback;

import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author promise
 * @date 2024/6/23 - 17:44
 */
@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {

  @Override
  public ItemClient create(Throwable cause) {
    return new ItemClient() {
      @Override
      public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
        log.error("查询商品失败！", cause);
        return Collections.emptyList();
      }

      @Override
      public void deductStock(List<OrderDetailDTO> items) {
        log.error("扣减商品库存失败！", cause);
        throw new RuntimeException(cause);
      }
    };
  }
}
