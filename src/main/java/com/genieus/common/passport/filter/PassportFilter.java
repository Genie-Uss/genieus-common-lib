package com.genieus.common.passport.filter;

import static com.genieus.common.passport.constant.PassportConstant.ATTR_PASSPORT;
import static com.genieus.common.passport.constant.PassportConstant.PASSPORT_HEADER;

import com.genieus.common.passport.model.Passport;
import com.genieus.common.passport.util.PassportUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PassportFilter implements Filter {

  private final PassportUtils passportUtils;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String encodedPassport = httpRequest.getHeader(PASSPORT_HEADER);

    if (encodedPassport != null) {
      try {
        Passport passport = passportUtils.decode(encodedPassport);
        request.setAttribute(ATTR_PASSPORT, passport);
      } catch (Exception e) {
        log.error("PassPort Decoding 작업 중 오류: {}", e.getMessage(), e);
      }
    } else {
      log.warn("Passport 헤더가 없습니다");
    }
    chain.doFilter(request, response);
  }
}