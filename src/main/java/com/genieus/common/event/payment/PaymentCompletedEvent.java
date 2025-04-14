package com.genieus.common.event.payment;

import com.genieus.common.event.DomainEvent;

public record PaymentCompletedEvent(Long orderId) implements DomainEvent {}
