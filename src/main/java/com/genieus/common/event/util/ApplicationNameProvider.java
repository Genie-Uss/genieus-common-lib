package com.genieus.common.event.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationNameProvider {

  @Value("${spring.application.name:unknown}")
  private String applicationName;

  private static String staticAppName;

  @PostConstruct
  public void init() {
    staticAppName = applicationName;
  }

  public static String getName() {
    return staticAppName;
  }
}
