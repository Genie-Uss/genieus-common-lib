package com.genieus.common.event.config;

import com.genieus.common.event.DomainEvent;
import com.genieus.common.event.util.ApplicationNameProvider;
import com.genieus.common.event.util.EventDispatcher;
import com.genieus.common.event.util.EventHandlerBeanPostProcessor;
import com.genieus.common.event.util.EventTypeRegistry;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

  @Bean
  public static EventHandlerBeanPostProcessor eventHandlerBeanPostProcessor() {
    return new EventHandlerBeanPostProcessor();
  }

  @Bean
  public ApplicationNameProvider applicationNameProvider() {
    return new ApplicationNameProvider();
  }

  @Bean
  public EventDispatcher eventDispatcher() {
    return new EventDispatcher();
  }

  @Bean
  public Function<String, Class<? extends DomainEvent>> eventTypeResolver() {
    return EventTypeRegistry.resolver();
  }
}
