package com.genieus.common.internal.client;

import com.genieus.common.internal.request.UseCouponRequest;
import com.genieus.common.internal.response.CouponClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CouponInternalClient {
    @GetMapping("/internal/v1/coupons/{couponId}")
    CouponClientResponse findCoupon(@PathVariable("couponId") Long couponId);

    @GetMapping("/internal/v1/coupons")
    List<CouponClientResponse> findCouponList(@RequestParam List<Long> couponIds);

    @PostMapping("/internal/v1/coupons/usage")
    CouponClientResponse useCoupon(@RequestBody UseCouponRequest useCouponRequest);
}
