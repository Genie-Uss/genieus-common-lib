package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 문자열 기반 이벤트 타입명(eventType)을 실제 이벤트 클래스와 매핑하는 레지스트리.
 * 주로 JSON 역직렬화 시 사용되며, 이벤트 디스패처와는 역할이 다르다.
 *
 * ⚠️ Spring Bean으로 등록하지 않는 이유:
 * - 모든 메서드와 상태가 static으로 구성되어 전역 접근이 가능함
 * - 의존성 주입 없이 어디서든 호출할 수 있어 단순한 유틸 클래스처럼 활용 가능
 * - 상태를 공유해야 하며, 오히려 빈으로 만들면 불필요한 DI만 늘어날 수 있음
 */
@Slf4j
public class EventTypeRegistry {

  private static final Map<String, Class<? extends DomainEvent>> registry =
      new ConcurrentHashMap<>();

  /**
   * eventType 문자열과 실제 이벤트 클래스 타입을 등록한다.
   *
   * @param eventType 예: "OrderCreatedEvent"
   * @param clazz     이벤트 클래스 타입 (DomainEvent 하위 클래스)
   */
  public static void register(String eventType, Class<? extends DomainEvent> clazz) {
    Class<? extends DomainEvent> existing = registry.put(eventType, clazz);
    if (existing != null && !existing.equals(clazz)) {
      log.warn("이벤트 타입 '{}' 매핑을 {}에서 {}로 덮어씁니다", eventType, existing.getName(), clazz.getName());
    } else if (existing == null) {
      log.debug("이벤트 타입 '{}' 매핑을 {}로 등록합니다", eventType, clazz.getName());
    }
  }

  /**
   * eventType 문자열로부터 매핑된 이벤트 클래스를 조회한다.
   *
   * @param eventType 예: "OrderCreatedEvent"
   * @return 해당 이벤트 타입에 매핑된 클래스 (없으면 null)
   */
  public static Class<? extends DomainEvent> resolve(String eventType) {
    return registry.get(eventType);
  }

  /**
   * 현재 등록된 모든 이벤트 타입과 매핑된 클래스를 반환한다.
   * 디버깅 및 모니터링 용도로 사용할 수 있다.
   *
   * @return 이벤트 타입과 클래스의 매핑 복사본
   */
  public static Map<String, Class<? extends DomainEvent>> getAllRegisteredTypes() {
    return new HashMap<>(registry);
  }
}
