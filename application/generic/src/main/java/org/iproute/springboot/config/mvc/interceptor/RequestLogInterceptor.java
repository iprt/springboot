package org.iproute.springboot.config.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.config.mvc.RequestLogUtils;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.repository.springboot.RequestLogBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * RequestLogInterceptor
 *
 * @author devops@kubectl.net
 * @since 2021/11/25
 */
@Component
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final String REQ_TIME_ATTRIBUTE = "RequestLogInterceptor_Request_Time";

    @Value("${spring.application.name:springboot}")
    private String applicationName;

    @Resource
    private RequestLogBeanMapper requestLogMapper;

    @Autowired
    private Executor asyncExecutor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQ_TIME_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if (!(handler instanceof HandlerMethod)) {
            log.info("RequestLogInterceptor.preHandle handler isn't HandlerMethod | class = {}", handler.getClass());
            return;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        RequestLog requestLog = hm.getMethodAnnotation(RequestLog.class);
        if (Objects.isNull(requestLog)) {
            return;
        }

        RequestLogBean logBean = RequestLogUtils.requestLogBean(request, true);
        logBean.setApplication(applicationName);

        Long reqTime = (Long) request.getAttribute(REQ_TIME_ATTRIBUTE);
        logBean.setRequestTime(new Date(reqTime));

        // 正确的接口描述为注解中的内容
        logBean.setRequestDesc(requestLog.value());

        asyncExecutor.execute(() -> {
            try {
                requestLogMapper.insert(logBean);
            } catch (Exception e) {
                // do nothing
                log.error("RequestLogInterceptor.preHandle Exception", e);
            }
        });
    }

}
