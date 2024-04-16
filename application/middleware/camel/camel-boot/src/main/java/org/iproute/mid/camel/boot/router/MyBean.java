package org.iproute.mid.camel.boot.router;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * MyBean
 *
 * @author devops@kubectl.net
 * @since 2022/7/20
 */
@Component("myBean")
public class MyBean {
    private int counter;

    @Value("${greeting}")
    private String say;

    public String saySomething(String body) {
        return String.format("%s I am invoked %d times", say, ++counter);
    }

}
