package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCanceledEvent(
    Long orderId,
    Long userId,
    Long couponId,
    List<OrderProductItem> orderProductItems,
    LocalDateTime canceledAt)
    implements DomainEvent {
}
