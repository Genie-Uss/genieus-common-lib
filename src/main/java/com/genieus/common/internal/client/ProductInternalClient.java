package com.genieus.common.internal.client;

import com.genieus.common.internal.response.ProductClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductInternalClient {
    @GetMapping("/internal/v1/products/{productId}")
    ProductClientResponse findProduct(@PathVariable("productId") Long productId);

    @GetMapping("/internal/v1/products")
    List<ProductClientResponse> findProductList(@RequestParam List<Long> productId);
}
