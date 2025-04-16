package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventDispatcher {

  private final Map<String, HandlerMethod> handlerMap = new HashMap<>();

  public void register(String eventType, Object bean, Method method) {
    handlerMap.put(eventType, new HandlerMethod(bean, method));
  }

  public void dispatch(String eventType, DomainEvent event) {
    HandlerMethod handlerMethod = handlerMap.get(eventType);
    if (handlerMethod == null) {
      throw new IllegalArgumentException("지원하지 않는 이벤트 타입입니다.: " + eventType);
    }
    try {
      handlerMethod.method().invoke(handlerMethod.bean(), event);
    } catch (Exception e) {
      throw new RuntimeException("이벤트 로직 처리에 실패하였습니다.", e);
    }
  }

  private record HandlerMethod(Object bean, Method method) {}
}
