package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventTypeRegistry {

  private static final Map<String, Class<? extends DomainEvent>> registry =
      new ConcurrentHashMap<>();

  public static void register(String eventType, Class<? extends DomainEvent> clazz) {
    registry.put(eventType, clazz);
  }

  public static Class<? extends DomainEvent> resolve(String eventType) {
    return registry.get(eventType);
  }

  public static Function<String, Class<? extends DomainEvent>> resolver() {
    return EventTypeRegistry::resolve;
  }
}
