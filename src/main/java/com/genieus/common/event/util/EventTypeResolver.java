package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.order.OrderCancelTriggerEvent;
import com.genieus.common.event.order.OrderCanceledEvent;
import com.genieus.common.event.order.OrderCompletedEvent;
import java.util.Map;
import java.util.function.Function;

public class EventTypeResolver {
  private static final Map<String, Class<? extends DomainEvent>> registry =
      Map.of(
          "OrderCanceledEvent", OrderCanceledEvent.class,
          "OrderCompletedEvent", OrderCompletedEvent.class,
          "OrderCancelTriggerEvent", OrderCancelTriggerEvent.class);

  public static final Function<String, Class<? extends DomainEvent>> RESOLVER =
      eventType -> {
        Class<? extends DomainEvent> clazz = registry.get(eventType);
        if (clazz == null) {
          throw new IllegalArgumentException("처리할 수 없는 eventType: " + eventType);
        }
        return clazz;
      };
}
