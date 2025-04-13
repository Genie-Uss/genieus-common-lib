package com.genieus.common.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genieus.common.auth.model.Passport;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PassportUtils {
  private final ObjectMapper objectMapper;

  public String encode(Passport passport) {
    try {
      byte[] bytes = objectMapper.writeValueAsBytes(passport);
      String encoded = Base64.getEncoder().encodeToString(bytes);
      return encoded;
    } catch (Exception e) {
      log.error("인코딩 오류: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public Passport decode(String encodedPassport) {
    try {
      byte[] bytes = Base64.getDecoder().decode(encodedPassport);
      Passport passport = objectMapper.readValue(bytes, Passport.class);
      return passport;
    } catch (Exception e) {
      log.error("디코딩 오류: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
