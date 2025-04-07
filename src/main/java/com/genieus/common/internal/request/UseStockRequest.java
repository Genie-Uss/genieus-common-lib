package com.genieus.common.internal.request;

import java.time.LocalDateTime;
import java.util.List;

public record UseStockRequest(List<UseStockItem> items) {
  public record UseStockItem(Long productId, Integer quantity, LocalDateTime orderedAt) {}
}
