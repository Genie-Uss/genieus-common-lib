package com.genieus.common.internal.request;

import java.util.List;

public record StockRequest(List<ProductStock> items) {

  public record ProductStock(Long productId, Integer quantity) {}
}