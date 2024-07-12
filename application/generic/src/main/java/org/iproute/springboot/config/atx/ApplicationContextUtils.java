package org.iproute.springboot.config.atx;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextUtils
 *
 * @author tech@intellij.io
 * @since 2021/11/27
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return ApplicationContextUtils.applicationContext;
    }

    /**
     * get bean by class
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * get bean by name
     *
     * @param name the name
     * @return the bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

}
