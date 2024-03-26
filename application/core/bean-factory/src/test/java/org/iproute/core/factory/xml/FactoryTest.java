package org.iproute.core.factory.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FactoryTest
 *
 * @author zhuzhenjie
 */
public class FactoryTest {
    final ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("beans.xml");

    @Test
    public void testBeanFactory() {

        Person person = (Person) appCtx.getBean("person");

        System.out.println("person = " + person);

        Person p = (Person) appCtx.getBean("p");

        System.out.println("p = " + p);
    }

    @Test
    public void testFactoryBean() {
        appCtx.getBean("conn", Connection.class).execute(
                "select * from table"
        );
    }

    @Test
    public void testFactory() {
        Connection conn = appCtx.getBean("conn1", Connection.class);
        conn.execute("select * from table");
    }

    @Test
    public void testStaticFactory() {
        Connection conn = appCtx.getBean("conn2", Connection.class);
        conn.execute("select * from table");
    }

}
