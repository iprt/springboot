package org.iproute.springboot.config.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AdditionalInterceptor
 *
 * @author winterfell
 * @since 2021/11/25
 */
@Component
@Slf4j
public class AdditionalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AdditionalInterceptor.preHandle");
        return true;
    }
}
