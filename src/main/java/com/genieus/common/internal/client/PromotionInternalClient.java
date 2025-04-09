package com.genieus.common.internal.client;

import com.genieus.common.internal.request.VerifyPromotionRequest;
import com.genieus.common.internal.response.PromotionClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PromotionInternalClient {
  @GetMapping("/internal/v1/promotions/{promotionId}")
  PromotionClientResponse findPromotion(@PathVariable("promotionId") Long promotionId);

  @GetMapping("/internal/v1/promotions")
  List<PromotionClientResponse> findPromotionList(@RequestParam List<Long> promotionIds);

  @PostMapping({"/internal/v1/promotions/verify"})
  List<PromotionClientResponse> verifyPromotion(
      @RequestBody VerifyPromotionRequest verifyPromotionRequest);
}
