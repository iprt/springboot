package org.iproute.springboot.config.mvc;

import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception e) {
        log.error("request uri = {}", request.getRequestURI(), e);
        return e.getMessage();
    }

}
