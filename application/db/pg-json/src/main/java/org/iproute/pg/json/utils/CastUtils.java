package org.iproute.pg.json.utils;

/**
 * CastUtils
 *
 * @author devops@kubectl.net
 */
public class CastUtils {

    private CastUtils() {
    }

    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

}
