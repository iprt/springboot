package org.iproute.mid.camel.boot.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CamelRouter
 *
 * @author tech@intellij.io
 * @since 2022/7/20
 */
@Component
public class CamelRouter extends RouteBuilder {

    @Autowired
    MyBean myBean;

    @Override
    public void configure() throws Exception {
        from("timer:hello?period={{myPeriod}}").routeId("hello")
                // and call the bean
                .bean(myBean, "saySomething")
                .to("stream:out");
    }
}
