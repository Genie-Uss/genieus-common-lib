package com.genieus.common.event.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventEnvelopeParser {

  private final ObjectMapper mapper;

  /**
   * JSON에서 eventType 값을 추출하고, 해당 문자열에 대응하는 DomainEvent 클래스 타입을 반환한다.
   *
   * @param json EventEnvelope 형태의 JSON 문자열
   * @return DomainEvent를 상속한 실제 이벤트 클래스 (등록된 타입 없으면 null 반환 가능)
   */
  public Class<? extends DomainEvent> resolveEventType(String json) {
    try {
      JsonNode root = mapper.readTree(json);
      String eventType = root.get("eventType").asText();
      return EventTypeRegistry.resolve(eventType);
    } catch (Exception e) {
      throw new RuntimeException("[resolveEventType] eventType 추출 실패", e);
    }
  }

  /**
   * 주어진 이벤트 타입 클래스를 기반으로 EventEnvelope<T> 객체로 역직렬화한다.
   *
   * @param json  EventEnvelope 형태의 JSON 문자열
   * @param clazz event 필드에 들어갈 실제 DomainEvent 하위 클래스
   * @return 역직렬화된 EventEnvelope<T> 객체
   */
  public EventEnvelope<? extends DomainEvent> deserialize(String json, Class<? extends DomainEvent> clazz) {
    try {
      // EventEnvelope의 제네릭 타입 T를 clazz로 설정한 JavaType을 생성
      JavaType javaType = mapper.getTypeFactory()
          .constructParametricType(EventEnvelope.class, clazz);

      // JSON → EventEnvelope<T> 로 역직렬화 수행
      return mapper.readValue(json, javaType);
    } catch (Exception e) {
      throw new RuntimeException("[deserialize] 역직렬화 실패", e);
    }
  }
}
