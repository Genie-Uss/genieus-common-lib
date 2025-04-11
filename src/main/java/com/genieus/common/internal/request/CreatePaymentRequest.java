package com.genieus.common.internal.request;

public record CreatePaymentRequest(Long orderId, Integer amount) {}
