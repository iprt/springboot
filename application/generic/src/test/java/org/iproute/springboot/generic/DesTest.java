package org.iproute.springboot.generic;

import org.iproute.springboot.utils.DesUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;

/**
 * DesTest
 *
 * @author zhuzhenjie
 * @since 2022/6/30
 */
public class DesTest {

    @Test
    public void de() throws GeneralSecurityException {
        String msg = "我喜欢你，可以做我女朋友吗？";
        String key = "One-More";

        String msg0 = DesUtils.decrypt(
                DesUtils.encrypt(msg, key),
                key);

        Assertions.assertEquals(msg, msg0);
    }
}
