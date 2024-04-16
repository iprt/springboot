package org.iproute.springboot.config.mvc;

import org.iproute.springboot.config.mvc.interceptor.AdditionalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AdditionalMvcConfig
 *
 * @author devops@kubectl.net
 * @since 2021/11/25
 */
@Configuration
public class AdditionalMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdditionalInterceptor additionalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(additionalInterceptor);
    }
}
