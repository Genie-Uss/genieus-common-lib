package com.genieus.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(1)
public class LogExecutionAspect {

  @Around("execution(* shop.genieus.order.application..*(..))")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String method = signature.toShortString();

    long start = System.currentTimeMillis();
    log.info("[START] {}", method);

    try {
      Object result = joinPoint.proceed();
      long duration = System.currentTimeMillis() - start;
      log.info("[END] {} time={}ms", method, duration);
      return result;
    } catch (Throwable e) {
      log.error("[EXCEPTION] {} - {}", method, e.getMessage(), e);
      throw e;
    }
  }
}
