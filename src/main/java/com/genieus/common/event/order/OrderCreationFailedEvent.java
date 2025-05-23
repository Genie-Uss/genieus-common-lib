package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.util.List;

public record OrderCreationFailedEvent(List<OrderProductItem> orderProductItems)
    implements DomainEvent {}
