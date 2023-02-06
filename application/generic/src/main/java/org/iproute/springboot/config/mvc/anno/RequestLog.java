package org.iproute.springboot.config.mvc.anno;

import java.lang.annotation.*;

/**
 * RequestLog
 *
 * @author zhuzhenjie
 * @since 2021/11/29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLog {

    /**
     * log desc
     *
     * @return the string
     */
    String value() default "";
}
