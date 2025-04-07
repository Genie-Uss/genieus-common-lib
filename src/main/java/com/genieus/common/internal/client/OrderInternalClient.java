package com.genieus.common.internal.client;

import com.genieus.common.internal.response.OrderClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrderInternalClient {
    @GetMapping("/internal/v1/orders/{orderId}")
    OrderClientResponse findOrder(@PathVariable("orderId") Long orderId);

    @GetMapping("/internal/v1/orders")
    List<OrderClientResponse> findOrderList(@RequestParam List<Long> orderIds);
}
