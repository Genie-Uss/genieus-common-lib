package com.genieus.common.internal.request;

public record AuthClientRequest(String token, String uri, String method) {}
