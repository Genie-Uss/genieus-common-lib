package com.genieus.common.event.product;

import com.genieus.common.event.DomainEvent;

public record ProductCreatedEvent(Long productId) implements DomainEvent {}
