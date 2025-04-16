package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class EventTypeRegistry {

  private static final Map<String, Class<? extends DomainEvent>> registry =
      new ConcurrentHashMap<>();

  public static void register(String eventType, Class<? extends DomainEvent> clazz) {
    registry.put(eventType, clazz);
  }

  public static Class<? extends DomainEvent> resolve(String eventType) {
    Class<? extends DomainEvent> clazz = registry.get(eventType);
    if (clazz == null) {
      throw new IllegalArgumentException("처리할 수 없는 eventType: " + eventType);
    }
    return clazz;
  }

  public static Function<String, Class<? extends DomainEvent>> resolver() {
    return EventTypeRegistry::resolve;
  }
}
