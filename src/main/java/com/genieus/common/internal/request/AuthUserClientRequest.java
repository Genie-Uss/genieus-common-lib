package com.genieus.common.internal.request;

import com.genieus.common.auth.model.RoleType;

public record AuthUserClientRequest(Long userId, String email, String hashedPassword, RoleType role) {}
