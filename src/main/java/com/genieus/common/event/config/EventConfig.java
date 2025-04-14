package com.genieus.common.event.config;

import com.genieus.common.event.util.ApplicationNameProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventConfig {

  @Bean
  public ApplicationNameProvider applicationNameProvider() {
    return new ApplicationNameProvider();
  }
}
