package org.iproute.pg.json.utils;

/**
 * CastUtils
 *
 * @author tech@intellij.io
 */
public class CastUtils {

    private CastUtils() {
    }

    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

}
