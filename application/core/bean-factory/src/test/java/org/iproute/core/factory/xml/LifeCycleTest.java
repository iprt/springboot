package org.iproute.core.factory.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * LifeCycleTest
 *
 * @author tech@intellij.io
 */
public class LifeCycleTest {
    final ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("beans.xml");

    @Test
    public void testInit() {
        Product product = appCtx.getBean("product", Product.class);
        System.out.println("finally : " + product);
    }

    @Test
    public void testDestroy() {
        appCtx.close();
    }
}
