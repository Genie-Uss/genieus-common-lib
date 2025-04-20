package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;

public record PaymentRequestedEvent(Long orderId, Integer amount) implements DomainEvent {}
