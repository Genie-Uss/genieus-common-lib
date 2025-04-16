package com.genieus.common.event.util;

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
