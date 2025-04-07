package com.genieus.common.internal.client;

import com.genieus.common.internal.response.UserClientResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserInternalClient {
    @GetMapping("/internal/v1/users/{userId}")
    UserClientResponse findUser(@PathVariable("userId") Long userId);

    @GetMapping("/internal/v1/users")
    List<UserClientResponse> findUserList(@RequestParam List<Long> userId);
}
