package com.genieus.common.auth.aop;

import com.genieus.common.auth.annotation.HasCustomer;
import com.genieus.common.auth.annotation.HasMasterAdmin;
import com.genieus.common.auth.annotation.HasRole;
import com.genieus.common.auth.annotation.HasShopAdmin;
import com.genieus.common.auth.context.PassportContext;
import com.genieus.common.auth.exception.UnauthorizedException;
import com.genieus.common.auth.model.Passport;
import com.genieus.common.auth.model.RoleType;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleCheckAspect {
  @Before(
      "@annotation(com.genieus.common.auth.annotation.HasRole) || "
          + "@annotation(com.genieus.common.auth.annotation.HasMasterAdmin) || "
          + "@annotation(com.genieus.common.auth.annotation.HasShopAdmin) || "
          + "@annotation(com.genieus.common.auth.annotation.HasCustomer)")
  public void checkRole(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Passport passport = PassportContext.getPassport();
    if (passport == null) {
      throw new UnauthorizedException("인증 정보가 없습니다.");
    }

    RoleType userRole = RoleType.valueOf(passport.getRole().name());

    HasRole hasRoleAnnotation = method.getAnnotation(HasRole.class);
    if (hasRoleAnnotation != null) {
      RoleType[] requiredRoles = hasRoleAnnotation.value();
      boolean hasRequiredRole = Arrays.stream(requiredRoles).anyMatch(role -> role == userRole);

      if (!hasRequiredRole) {
        throw new UnauthorizedException("필요한 권한이 없습니다: " + Arrays.toString(requiredRoles));
      }
      return;
    }

    if (method.isAnnotationPresent(HasMasterAdmin.class) && userRole != RoleType.MASTER_ADMIN) {
      throw new UnauthorizedException("MASTER_ADMIN 권한이 필요합니다.");
    }

    if (method.isAnnotationPresent(HasShopAdmin.class) && userRole != RoleType.SHOP_ADMIN) {
      throw new UnauthorizedException("SHOP_ADMIN 권한이 필요합니다.");
    }

    if (method.isAnnotationPresent(HasCustomer.class) && userRole != RoleType.CUSTOMER) {
      throw new UnauthorizedException("CUSTOMER 권한이 필요합니다.");
    }
  }
}
