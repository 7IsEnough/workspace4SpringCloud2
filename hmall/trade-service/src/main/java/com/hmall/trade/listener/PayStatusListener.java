package com.hmall.trade.listener;

import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author promise
 * @date 2024/12/28 - 20:37
 */
@Component
@RequiredArgsConstructor
public class PayStatusListener {

  private final IOrderService orderService;

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "trade.pay.success.queue", durable = "true"),
      exchange = @Exchange("pay.direct"),
      key = "pay.success"
  ))
  public void listenPaySuccess(Long orderId) {
    // 查询订单
    Order order = orderService.getById(orderId);
    // 判断订单状态，是否为未支付
    if (order == null || order.getStatus() != 1) {
      // 不做处理
      return;
    }

    // 标记订单状态为已支付
    orderService.markOrderPaySuccess(orderId);
  }
}
