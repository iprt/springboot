package org.iproute.springboot.config.mvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * AdditionalInterceptor
 *
 * @author tech@intellij.io
 * @since 2021/11/25
 */
@Component
@Slf4j
public class AdditionalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        log.info("AdditionalInterceptor.preHandle");
        return true;
    }
}
