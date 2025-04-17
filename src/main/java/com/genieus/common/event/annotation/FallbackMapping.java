package com.genieus.common.event.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FallbackMapping {
  /**
   * Kafka 토픽 이름 (예: "order-events")
   * EventTypeMapping과 동일한 토픽 값을 가져야 매핑됩니다.
   */
  String topic();

  /**
   * 이벤트 타입 (예: "OrderCreated")
   * EventTypeMapping과 동일한 이벤트 타입 값을 가져야 매핑됩니다.
   * 이 메서드는 주 이벤트 핸들러가 실패했을 때 호출됩니다.
   */
  String eventType() default "";
}
