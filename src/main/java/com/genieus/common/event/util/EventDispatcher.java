package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.EventEnvelope;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Kafka 등으로부터 수신한 이벤트의 topic + eventType 에 따라, 사전에 등록된 핸들러 메서드를 찾아 실행하는 이벤트 디스패처.
 *
 * <p>- @EventTypeMapping 애노테이션으로 등록된 메서드를 토픽 + 이벤트 타입 기준으로 매핑 - 등록되지 않은 이벤트는 무시 (예외 발생하지 않음) - 이벤트
 * 처리 중 예외 발생 시 fallback 메서드가 존재하면 실행
 */
@Slf4j
@Component
public class EventDispatcher {

  // 토픽 + 이벤트타입을 키로 사용하는 핸들러 맵
  private final Map<EventHandlerKey, HandlerMethod> handlerMap = new ConcurrentHashMap<>();

  /**
   * 특정 토픽 + 이벤트 타입에 대한 처리 메서드를 등록한다.
   *
   * @param topic 수신되는 Kafka 토픽 이름
   * @param eventType 이벤트 타입 이름 (예: "OrderCreated", "PaymentFailed")
   * @param bean 이벤트를 처리할 메서드를 가진 빈 객체
   * @param method 실제 처리 메서드 (파라미터는 DomainEvent 하위 타입 1개)
   * @param fallbackMethod 처리 실패 시 호출될 fallback 메서드 (선택, 없을 수 있음)
   */
  public void register(
      String topic, String eventType, Object bean, Method method, Method fallbackMethod) {
    handlerMap.put(
        new EventHandlerKey(topic, eventType), new HandlerMethod(bean, method, fallbackMethod));
    log.info("이벤트 핸들러 등록됨 - topic: {}, eventType: {}, bean: {}", topic, eventType, bean);
  }

  /**
   * 주어진 토픽과 이벤트 타입에 해당하는 핸들러를 찾아 이벤트를 처리한다.
   *
   * @param topic Kafka에서 수신한 실제 토픽명
   * @param eventType EventEnvelope 내의 eventType 필드
   * @param event 디스패치할 이벤트 객체 (DomainEvent 구현체)
   */
  public void dispatch(String topic, String eventType, DomainEvent event) {
    EventHandlerKey key = new EventHandlerKey(topic, eventType);
    HandlerMethod handlerMethod = handlerMap.get(key);
    if (handlerMethod == null) {
      // 핸들러 없으면 무시
      return;
    }
    try {
      // 리플렉션으로 메서드 실행
      handlerMethod.method().invoke(handlerMethod.bean(), event);
    } catch (Exception e) {
      // 예외는 route() 단에서 잡히고 fallback 호출로 이어짐
      throw new RuntimeException(e.getCause());
    } 
  }

  /**
   * 이벤트 처리 실패 시, 등록된 fallback 메서드를 찾아 실행한다.
   *
   * @param topic Kafka 수신 토픽
   * @param eventType 이벤트 타입명
   * @param envelope 원본 이벤트 봉투 (null 가능)
   * @param ex 처리 중 발생한 예외
   *     <p>책임 범위: - 등록된 fallback 메서드가 있다면 해당 메서드 실행 - fallback 메서드가 없다면 무시 - fallback 메서드 실행 중 오류
   *     발생 시 log.error만 출력하고 전파하지 않음
   */
  public void fallback(
      String topic, String eventType, EventEnvelope<? extends DomainEvent> envelope, Throwable ex) {
    EventHandlerKey key = new EventHandlerKey(topic, eventType);
    HandlerMethod handler = handlerMap.get(key);
    if (handler == null || handler.fallbackMethod() == null) {
      return;
    }
    try {
      handler.fallbackMethod().invoke(handler.bean(), envelope, ex);
    } catch (Exception e) {
      log.warn("[fallback] fallback 실행 중 오류 발생 - topic={}, eventType={}, key={}",
          topic, eventType, key, e);
    }
  }

  /**
   * 핸들러 메서드 정보를 보관하기 위한 내부 record 클래스.
   *
   * @param bean: 실제 객체 인스턴스
   * @param method: 해당 이벤트를 처리할 메서드
   * @param fallbackMethod: 실패 시 호출할 fallback 메서드 (nullable)
   */
  private record HandlerMethod(Object bean, Method method, Method fallbackMethod) {}

  /** 이벤트 핸들러를 고유하게 식별하기 위한 복합 키 (topic + eventType). */
  private record EventHandlerKey(String topic, String eventType) {
    public String asKey() {
      return topic + "::" + eventType;
    }

    @Override
    public String toString() {
      return asKey();
    }
  }
}
