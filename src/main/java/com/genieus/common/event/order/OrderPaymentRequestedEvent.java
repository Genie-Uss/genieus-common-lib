package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;
import java.time.LocalDateTime;

public record OrderPaymentRequestedEvent(
    Long orderId,
    Integer amount,
    LocalDateTime paymentRequestedAt)
    implements DomainEvent {}
