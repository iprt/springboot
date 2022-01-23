package org.iproute.springboot.config.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.config.mvc.RequestLogUtils;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.repository.zhuzhenjie.RequestLogBeanMapper;
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
 * @author winterfell
 * @since 2021/11/25
 */
@Component
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private RequestLogBeanMapper requestLogMapper;

    @Autowired
    private Executor asyncExecutor;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            log.info("RequestLogInterceptor.preHandle handler isn't HandlerMethod | class = {}", handler.getClass());
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        RequestLog requestLog = hm.getMethodAnnotation(RequestLog.class);
        if (Objects.isNull(requestLog)) {
            return true;
        }

        RequestLogBean logBean = RequestLogUtils.requestLogBean(request);

        logBean.setApplication(application());
        logBean.setRequestTime(new Date());
        logBean.setRequestDesc(requestLog.value());

        asyncExecutor.execute(() -> {
            try {
                requestLogMapper.insert(logBean);
            } catch (Exception e) {
                // do nothing
                log.error("RequestLogInterceptor.preHandle Exception | {}", e.getMessage());
            }
        });

        return true;
    }

    private String application() {
        return StringUtils.isBlank(applicationName) ? "" : applicationName;
    }

}
