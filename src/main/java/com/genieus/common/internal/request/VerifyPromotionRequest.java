package com.genieus.common.internal.request;

import java.time.LocalDateTime;
import java.util.List;

public record VerifyPromotionRequest(List<PromotionItem> items, LocalDateTime orderedAt) {

    public record PromotionItem(Long productId, Long promotionId) {}
}
