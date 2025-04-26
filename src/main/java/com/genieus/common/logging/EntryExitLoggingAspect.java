package com.genieus.common.logging;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(1)
public class EntryExitLoggingAspect {

  // 1) 컨트롤러
  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void controllerPointcut() {}

  // 2) 이벤트 타입 매핑
  @Pointcut("@annotation(com.genieus.common.event.annotation.EventTypeMapping)")
  public void eventTypeMappingPointcut() {}

  // 3) 트랜잭션 이벤트 리스너
  @Pointcut("@annotation(org.springframework.transaction.event.TransactionalEventListener)")
  public void txEventListenerPointcut() {}

  // 4) 스케줄러
  @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
  public void scheduledPointcut() {}

  // 위 네 지점 중 하나라도 걸리면 적용
  @Pointcut("controllerPointcut() || eventTypeMappingPointcut() || txEventListenerPointcut() || scheduledPointcut()")
  public void entryPoints() {}

  @Around("entryPoints()")
  public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
    String signature = pjp.getSignature().toShortString();
    Object[] args = pjp.getArgs();
    long start = System.currentTimeMillis();

    log.info("[ENTRY] {} → args={}", signature, Arrays.toString(args));
    try {
      Object result = pjp.proceed();
      long duration = System.currentTimeMillis() - start;
      log.info("[EXIT]  {} → result={}, time={}ms", signature, result, duration);
      return result;
    } catch (Throwable tx) {
      log.error("[ERROR] {} → {}", signature, tx.getMessage(), tx);
      throw tx;
    }
  }
}
