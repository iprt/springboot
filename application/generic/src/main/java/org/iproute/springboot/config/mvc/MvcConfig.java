package org.iproute.springboot.config.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.iproute.springboot.config.mvc.anno.PrefixRestController;
import org.iproute.springboot.config.mvc.interceptor.RequestLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MvcConfig
 *
 * @author devops@kubectl.net
 * @since 2021/11/25
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private RequestLogInterceptor requestLogInterceptor;


    /**
     * interceptor config
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor);
    }

    /**
     * path config
     *
     * @param configurer PathMatchConfigurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix("/prefix", c -> c.isAnnotationPresent(PrefixRestController.class));
    }

    @Bean
    public ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // localDateTime按照 "yyyy-MM-dd HH:mm:ss"的格式进行序列化、反序列化
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}
