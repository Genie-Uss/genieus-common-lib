package com.genieus.common.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.genieus.common.auth.context.PassportContext;
import com.genieus.common.auth.filter.PassportFilter;
import com.genieus.common.auth.resolver.PassportArgumentResolver;
import com.genieus.common.auth.util.PassportUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class PassportWebConfig implements WebMvcConfigurer {

  @Bean
  public FilterRegistrationBean<PassportFilter> passportFilterRegistration(
      PassportFilter passportFilter) {
    FilterRegistrationBean<PassportFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(passportFilter);
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registrationBean;
  }

  @Bean
  public PassportFilter passportFilter(PassportUtils passportUtils) {
    return new PassportFilter(passportUtils);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(passportArgumentResolver());
  }

  @Bean
  public PassportArgumentResolver passportArgumentResolver() {
    return new PassportArgumentResolver();
  }

  @Bean
  public ObjectMapper passportObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  @Bean
  public PassportUtils passportUtils(ObjectMapper passportObjectMapper) {
    return new PassportUtils(passportObjectMapper);
  }

  @Bean
  public PassportContext passportContext() {
    return new PassportContext();
  }
}
