package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRouter {

  private final EventEnvelopeParser parser;
  private final EventDispatcher dispatcher;

  /**
   * Kafka로부터 수신한 메시지를 파싱하고, 이벤트 핸들러에 디스패치한다.
   *
   * @param topic Kafka 수신 토픽
   * @param json Kafka 메시지 payload (EventEnvelope JSON)
   */
  public void route(String topic, String json) {

    // 1. 이벤트 타입을 파악하지 못한 경우 → 무시
    Class<? extends DomainEvent> clazz = resolveEventType(topic, json);
    if (clazz == null) return;

    // 2. 이벤트 타입은 알지만 역직렬화에 실패한 경우 → 무시
    EventEnvelope<? extends DomainEvent> envelope = deserializeEnvelope(json, clazz);
    if (envelope == null) return;

    // 3. 이벤트 디스패치 실패 시 fallback 처리
    try {
      dispatcher.dispatch(topic, envelope.getEventType(), envelope.getEvent());
    } catch (Exception dispatchEx) {
      log.error("[route] 서비스에서 이벤트 처리 중 예외 발생 - topic: {}, envelope: {}, error: {} ", topic, envelope, dispatchEx.getMessage());
      dispatcher.fallback(topic, envelope.getEventType(), envelope, dispatchEx.getCause());
    }
  }

  private Class<? extends DomainEvent> resolveEventType(String topic, String json) {
    try {
      return parser.resolveEventType(json);
    } catch (Exception e) {
      log.warn(
          "[resolveEventType] 이벤트 타입 추출 실패 - topic={}, json={}, error: {}",
          topic,
          json,
          e.getMessage());
      return null;
    }
  }

  private EventEnvelope<? extends DomainEvent> deserializeEnvelope(
      String json, Class<? extends DomainEvent> clazz) {
    try {
      return parser.deserialize(json, clazz);
    } catch (Exception e) {
      log.warn(
          "[deserializeEnvelope] 이벤트 역직렬화 실패 - json ={}, clazz ={}, error: {}",
          json,
          clazz,
          e.getMessage());
      return null;
    }
  }
}
