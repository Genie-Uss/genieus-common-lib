package com.genieus.common.internal.client;

import com.genieus.common.internal.request.AuthUserClientRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthInternalClient {
  @PostMapping("/internal/v1/auth")
  void registerAuthUser(@RequestBody AuthUserClientRequest request);
}
