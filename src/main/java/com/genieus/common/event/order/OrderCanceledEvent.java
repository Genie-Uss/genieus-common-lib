package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCanceledEvent(
    Long orderId,
    Long userid,
    Long couponId,
    List<OrderProductItem> orderProductItems,
    LocalDateTime canceledAt)
    implements DomainEvent {
  public record OrderProductItem(Long productId, Integer quantity) {}
}
