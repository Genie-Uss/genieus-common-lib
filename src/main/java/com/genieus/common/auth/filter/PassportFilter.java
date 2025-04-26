package com.genieus.common.auth.filter;

import static com.genieus.common.auth.constant.PassportConstant.ATTR_PASSPORT;
import static com.genieus.common.auth.constant.PassportConstant.PASSPORT_HEADER;

import com.genieus.common.auth.context.PassportContext;
import com.genieus.common.auth.model.Passport;
import com.genieus.common.auth.util.PassportUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
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

    try {
        try {
          Passport passport = passportUtils.decode(encodedPassport);
          request.setAttribute(ATTR_PASSPORT, passport);

          PassportContext.setPassport(passport);
        } catch (Exception e) {
          log.error("PassPort Decoding 작업 중 오류: {}", e.getMessage(), e);
        }
      chain.doFilter(request, response);
    } finally {
      PassportContext.clear();
    }
  }
}
