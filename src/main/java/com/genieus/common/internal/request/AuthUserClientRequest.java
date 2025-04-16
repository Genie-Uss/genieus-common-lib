package com.genieus.common.internal.request;

import com.genieus.common.auth.model.RoleType;

public record AuthUserClientRequest(String email, String hashedPassword, RoleType role) {}
