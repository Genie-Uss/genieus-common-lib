package com.genieus.common.internal.client;

import com.genieus.common.internal.request.CreatePaymentRequest;
import com.genieus.common.internal.response.PaymentClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentInternalClient {
  @GetMapping("/internal/v1/payments/{paymentId}")
  PaymentClientResponse findPayment(@PathVariable("paymentId") Long paymentId);

  @GetMapping("/internal/v1/payments")
  List<PaymentClientResponse> findPaymentList(@RequestParam List<Long> paymentIds);

  @PostMapping("/internal/v1/payments")
  PaymentClientResponse createPayment(@RequestBody CreatePaymentRequest request);
}
