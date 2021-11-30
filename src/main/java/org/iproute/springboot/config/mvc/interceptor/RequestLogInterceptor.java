package org.iproute.springboot.config.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.repository.zhuzhenjie.RequestLogBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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

        RequestLogBean logBean = RequestLogBean.builder()
                .application(application())
                .method(method(request))
                .uri(uri(request))
                .contentType(contentType(request))
                .queryString(queryString(request))
                .body(body(request))
                .userAgent(userAgent(request))
                .uid(-1L)
                .uname(StringUtils.EMPTY)
                .ip(ip(request))
                .requestDesc(requestLog.value())
                .build();

        try {
            requestLogMapper.insert(logBean);
        } catch (Exception e) {
            // do nothing
            log.error("RequestLogInterceptor.preHandle Exception | {}", e.getMessage());
        }
        return true;
    }

    private String application() {
        return StringUtils.isBlank(applicationName) ? "" : applicationName;
    }

    private String method(HttpServletRequest request) {
        return request.getMethod();
    }


    private String uri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private String contentType(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.CONTENT_TYPE);
    }

    private String queryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return StringUtils.isBlank(queryString) ? "" : queryString;
    }

    private String body(HttpServletRequest request) {
        // TODO 源码可知 ContentCachingRequestWrapper 对 inputStream只读了一次，故要要重写ServletInputStream；对于Security环境，需要摸清filter的加载顺序
        return "";
    }

    private String userAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        return StringUtils.isBlank(userAgent) ? "" : userAgent;
    }

    /**
     * 获取Ip地址
     *
     * @param request the request
     * @return ip address
     */
    String ip(HttpServletRequest request) {
        String xIp = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIp;
        if (StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(xFor) ? "127.0.0.1" : xFor;
    }

}
