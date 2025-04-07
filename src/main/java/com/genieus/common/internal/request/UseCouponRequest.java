package com.genieus.common.internal.request;

import java.time.LocalDateTime;

public record UseCouponRequest(Long userId, Long couponId, LocalDateTime orderedAt) {}
