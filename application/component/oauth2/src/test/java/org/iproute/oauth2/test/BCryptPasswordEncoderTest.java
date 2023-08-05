package org.iproute.oauth2.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoderTest
 *
 * @author zhuzhenjie
 * @since 2023/8/6
 */
@Slf4j
public class BCryptPasswordEncoderTest {

    @Test
    public void encode() {
        String raw = "client-secret-demo";
        log.info("password : {}", new BCryptPasswordEncoder().encode(raw));
    }

}
