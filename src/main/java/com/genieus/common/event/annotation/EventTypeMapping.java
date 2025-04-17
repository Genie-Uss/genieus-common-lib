package com.genieus.common.event.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventTypeMapping {
  /**
   * Kafka 토픽 이름 (예: "order-events")
   */
  String topic();

  /**
   * 이벤트 타입 (예: "OrderCreated")
   * 빈 문자열("")로 설정하면 메서드 파라미터 타입에서 이벤트 타입을 유추합니다.
   */
  String eventType() default "";
}
