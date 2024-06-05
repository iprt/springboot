package org.iproute.pg.json.test;

import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.service.SayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SayTest
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class SayTest {
    private final SayService sayService;

    @Test
    public void testSingle() {
        Assertions.assertEquals("Hello", sayService.say());
    }

    @Qualifier("helloSayService")
    @Autowired
    private SayService helloSay;


    @Qualifier("hiSayService")
    @Autowired
    private SayService hiSay;

    @Test
    public void testMulti() {
        Assertions.assertEquals("Hello", helloSay.say());
        Assertions.assertEquals("Hi", hiSay.say());
    }

}
