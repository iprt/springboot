package org.iproute.springboot.design.tree.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * ServiceProvideFilter
 *
 * @author devops@kubectl.net
 */
@Component
@Order(1)
@Slf4j
public class ServiceProvideFilter implements Filter {

    private static final Set<String> IgnoreURI = new HashSet<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (WebEnableService.canProvideService()) {
            chain.doFilter(request, response);
            return;
        }

        if (request instanceof HttpServletRequest) {
            HttpServletRequest hsr = (HttpServletRequest) request;
            String requestURI = hsr.getRequestURI();
            if (IgnoreURI.contains(requestURI)) {
                chain.doFilter(request, response);
                return;
            }
        }

        returnJson(response, "{\"code\":9999,\"msg\":\"Disable to provide service\"}");
    }

    private void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    static {
        IgnoreURI.add(WebEnableService.DISABLE_URI);
        IgnoreURI.add(WebEnableService.ENABLE_URI);
    }

}
