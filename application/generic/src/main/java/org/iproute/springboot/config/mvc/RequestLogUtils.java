package org.iproute.springboot.config.mvc;

import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestLogUtils
 *
 * @author devops@kubectl.net
 * @since 2021/11/30
 */
public class RequestLogUtils {

    /**
     * Request log bean request log bean.
     *
     * @param request the request
     * @return the request log bean
     */
    public static RequestLogBean requestLogBean(HttpServletRequest request,boolean success) {
        return RequestLogBean.builder()
                .method(method(request))
                .uri(uri(request))
                .success(success)
                .contentType(contentType(request))
                .queryString(queryString(request))
                .body(body(request))
                .userAgent(userAgent(request))
                .uid(-1L)
                .uname(StringUtils.EMPTY)
                .ip(ip(request))
                .build();
    }


    private static String method(HttpServletRequest request) {
        return request.getMethod();
    }


    private static String uri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private static String contentType(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.CONTENT_TYPE);
    }

    private static String queryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return StringUtils.isBlank(queryString) ? "" : queryString;
    }

    private static String body(HttpServletRequest request) {
        // TODO 源码可知 ContentCachingRequestWrapper 对 inputStream只读了一次，故要要重写ServletInputStream；对于Security环境，需要摸清filter的加载顺序
        return "";
    }

    private static String userAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        return StringUtils.isBlank(userAgent) ? "" : userAgent;
    }

    /**
     * 获取Ip地址
     *
     * @param request the request
     * @return ip address
     */
    private static String ip(HttpServletRequest request) {
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
