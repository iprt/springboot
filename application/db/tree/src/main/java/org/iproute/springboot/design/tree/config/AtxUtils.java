package org.iproute.springboot.design.tree.config;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtxConfig
 *
 * @author tech@intellij.io
 */
@Component
@Getter
public class AtxUtils implements ApplicationContextAware {

    private final AtomicReference<ApplicationContext> atx = new AtomicReference<>();

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.atx.set(applicationContext);
    }
}
