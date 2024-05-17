package org.iproute.springboot.config.mvc.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.mvc.RequestLogUtils;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.repository.springboot.RequestLogBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.Executor;

/**
 * RestExceptionHandler
 *
 * @author devops@kubectl.net
 * @since 2021/11/27
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @Value("${spring.application.name:springboot}")
    private String applicationName;

    @Resource
    private RequestLogBeanMapper requestLogBeanMapper;

    @Resource
    private Executor asyncExecutor;

    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleException(HttpServletRequest request, Exception e) {
        log.error("request uri = {}", request.getRequestURI(), e);

        RequestLogBean reqLogBean = RequestLogUtils.requestLogBean(request, false);
        // TODO 不同的Exception报错时间可以不同的调整
        reqLogBean.setRequestTime(new Date());

        reqLogBean.setApplication(applicationName);
        String eMsg = e.getMessage();

        reqLogBean.setRequestDesc(eMsg);

        asyncExecutor.execute(() -> {
            try {
                requestLogBeanMapper.insert(reqLogBean);
            } catch (Exception ex) {
                log.error("RestExceptionHandler->RequestLogBean |{}", ex.getMessage());
            }
        });

        return eMsg;
    }
}
