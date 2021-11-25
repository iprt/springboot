package org.iproute.springboot.config.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.entities.RequestLog;
import org.iproute.springboot.repository.zhuzhenjie.RequestLogMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestLogInterceptor
 *
 * @author winterfell
 * @since 2021/11/25
 */
@Component
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private RequestLogMapper requestLogMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        requestLogMapper.insert(RequestLog.builder()
                .application(applicationName)
                .uri(uri)
                .build());

        return true;
    }
}
