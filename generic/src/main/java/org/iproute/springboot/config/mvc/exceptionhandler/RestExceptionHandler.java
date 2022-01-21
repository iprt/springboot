package org.iproute.springboot.config.mvc.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.mvc.RequestLogUtils;
import org.iproute.springboot.entities.po.RequestErrorLogBean;
import org.iproute.springboot.repository.zhuzhenjie.RequestErrorLogBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * RestExceptionHandler
 *
 * @author winterfell
 * @since 2021/11/27
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private RequestErrorLogBeanMapper requestErrorLogBeanMapper;

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception e) {
        log.error("request uri = {}", request.getRequestURI(), e);
        try {
            RequestErrorLogBean requestErrorLogBean = RequestLogUtils.requestErrorLogBean(request);
            requestErrorLogBean.setApplication(applicationName);
            requestErrorLogBean.setExceptionMsg(e.getMessage());

            requestErrorLogBeanMapper.insert(requestErrorLogBean);
        } catch (Exception ex) {
            log.error("RestExceptionHandler->RequestErrorLogBeanMapper |{}", ex.getMessage());
        }
        return e.getMessage();
    }
}
