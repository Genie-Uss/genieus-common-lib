package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;

public record CouponRestoredEvent(Long orderId, Long userId, Long couponId)
    implements DomainEvent {}
