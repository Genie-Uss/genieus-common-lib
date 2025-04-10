package com.genieus.common.internal.response;

import com.genieus.common.auth.constant.PassportConstant;

public record AuthClientResponse(String encodedPassport, String passportHeaderKey) {
  public static AuthClientResponse from(String encodedPassport, String passportHeaderKey) {
    return new AuthClientResponse(encodedPassport, passportHeaderKey);
  }

  public static AuthClientResponse from(String encodedPassport) {
    return AuthClientResponse.from(encodedPassport, PassportConstant.PASSPORT_HEADER);
  }
}
