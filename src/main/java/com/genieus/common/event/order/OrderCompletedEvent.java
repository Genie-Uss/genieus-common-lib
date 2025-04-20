package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCompletedEvent(
    Long orderId,
    Long userId,
    Long couponId,
    List<OrderProductItem> orderProductItems,
    LocalDateTime completedAt)
    implements DomainEvent {
}
