package com.genieus.common.internal.request;

public record CreatePaymentRequest(
    Long userId, Long orderId, Long pgId, Integer price, String paymentMethod) {}
