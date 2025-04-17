package com.genieus.common.event.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genieus.common.event.util.ApplicationNameProvider;
import com.genieus.common.event.util.EventDispatcher;
import com.genieus.common.event.util.EventEnvelopeParser;
import com.genieus.common.event.util.EventHandlerBeanPostProcessor;
import com.genieus.common.event.util.EventRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

  /**
   * 스프링 빈 초기화 후, @EventTypeMapping 애노테이션을 분석하여 핸들러를 등록하는 후처리기
   * → static 으로 등록되어야 BeanPostProcessor로 동작함
   */
  @Bean
  public static EventHandlerBeanPostProcessor eventHandlerBeanPostProcessor() {
    return new EventHandlerBeanPostProcessor();
  }

  /**
   * 현재 서비스의 이름을 반환하는 provider (이벤트의 source 필드에 활용)
   */
  @Bean
  public ApplicationNameProvider applicationNameProvider() {
    return new ApplicationNameProvider();
  }

  /**
   * 이벤트 디스패처: topic + eventType에 따라 핸들러 메서드 실행
   */
  @Bean
  public EventDispatcher eventDispatcher() {
    return new EventDispatcher();
  }

  /**
   * EventEnvelope 파싱기 (JSON → EventEnvelope<T>)
   */
  @Bean
  public EventEnvelopeParser eventEnvelopeParser(ObjectMapper objectMapper) {
    return new EventEnvelopeParser(objectMapper);
  }

  /**
   * Kafka 수신 후, 파싱 + 디스패치까지 수행하는 라우터
   */
  @Bean
  public EventRouter eventRouter(EventEnvelopeParser parser, EventDispatcher dispatcher) {
    return new EventRouter(parser, dispatcher);
  }
}
