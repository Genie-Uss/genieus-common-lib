package com.genieus.common.auth.context;

import com.genieus.common.auth.model.Passport;
import org.springframework.stereotype.Component;

@Component
public class PassportContext {
  private static final ThreadLocal<Passport> CONTEXT = new ThreadLocal<>();

  public static Passport getPassport() {
    return CONTEXT.get();
  }

  public static void setPassport(Passport passport) {
    CONTEXT.set(passport);
  }

  public static void clear() {
    CONTEXT.remove();
  }
}
