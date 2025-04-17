package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.annotation.EventTypeMapping;
import com.genieus.common.event.annotation.FallbackMapping;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 모든 스프링 빈을 후처리하면서, @EventTypeMapping 애노테이션이 붙은 메서드를 탐색해 Dispatcher 및 Registry에 등록한다. - Dispatcher:
 * topic + eventType → handler 메서드 및 fallback 메서드 등록 - EventTypeRegistry: eventType → DomainEvent
 * 클래스 매핑
 */
@Component
public class EventHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

  private ApplicationContext context;

  @Override
  public Object postProcessAfterInitialization(Object bean, @NonNull String beanName)
      throws BeansException {
    // 1. 모든 메서드를 리플렉션으로 검사
    for (Method method : bean.getClass().getDeclaredMethods()) {

      // 2. @EventTypeMapping 애노테이션이 붙은 메서드만 필터링
      if (method.isAnnotationPresent(EventTypeMapping.class)) {
        // 3. 이벤트 핸들러 메서드 파라미터 유효성 검사
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !DomainEvent.class.isAssignableFrom(parameterTypes[0])) {
          throw new IllegalArgumentException(
              "@EventTypeMapping 메서드는 DomainEvent 하나만 받아야 합니다: " + method);
        }

        // 4. topic, eventType 추출
        EventTypeMapping mapping = method.getAnnotation(EventTypeMapping.class);
        String topic = mapping.topic();
        String eventType =
            mapping.eventType().isBlank() ? parameterTypes[0].getSimpleName() : mapping.eventType();

        // 5. fallback 메서드 탐색 (@FallbackMapping 포함 & topic, eventType 일치)
        Method fallbackMethod =
            Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(FallbackMapping.class))
                .filter(
                    m -> {
                      FallbackMapping fallback = m.getAnnotation(FallbackMapping.class);
                      return fallback.topic().equals(topic)
                          && fallback.eventType().equals(eventType);
                    })
                .findFirst()
                .orElse(null);

        // 6. Dispatcher에 등록
        context
            .getBean(EventDispatcher.class)
            .register(topic, eventType, bean, method, fallbackMethod);

        // 7. 이벤트 타입 registry 등록
        @SuppressWarnings("unchecked")
        Class<? extends DomainEvent> eventClass = (Class<? extends DomainEvent>) parameterTypes[0];
        EventTypeRegistry.register(eventType, eventClass);
      }
    }
    return bean;
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext)
      throws BeansException {
    this.context = applicationContext;
  }
}
