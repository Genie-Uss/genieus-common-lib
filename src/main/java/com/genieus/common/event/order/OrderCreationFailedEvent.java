package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreationFailedEvent(
    Long userId,
    List<OrderProductItem> orderProductItems,
    LocalDateTime orderedAt)
    implements DomainEvent {}
