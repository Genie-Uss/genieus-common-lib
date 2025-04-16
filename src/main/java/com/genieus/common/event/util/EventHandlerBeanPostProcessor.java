package com.genieus.common.event.util;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.annotation.EventTypeMapping;
import java.lang.reflect.Method;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

  private ApplicationContext context;

  @Override
  public Object postProcessAfterInitialization(Object bean, @NonNull String beanName)
      throws BeansException {
    for (Method method : bean.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(EventTypeMapping.class)) {
        String eventType = method.getAnnotation(EventTypeMapping.class).value();
        context.getBean(EventDispatcher.class).register(eventType, bean, method);

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !DomainEvent.class.isAssignableFrom(parameterTypes[0])) {
          throw new IllegalArgumentException(
              "@EventTypeMapping 메서드는 DomainEvent 하나만 받아야 합니다: " + method);
        }

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
