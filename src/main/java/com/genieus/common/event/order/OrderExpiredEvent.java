package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record OrderExpiredEvent(
    Long orderId,
    Long userid,
    List<OrderProductItem> orderProductItems,
    LocalDateTime expiredAt)
    implements DomainEvent {}