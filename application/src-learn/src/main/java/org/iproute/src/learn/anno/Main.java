package org.iproute.src.learn.anno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 2022/8/1
 */
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext atx =
                new AnnotationConfigApplicationContext(Config.class);

        DemoBean bean = atx.getBean(DemoBean.class);

        System.out.println(bean);
    }
}
