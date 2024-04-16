package org.iproute.pg.json.utils;

/**
 * CastUtils
 *
 * @author zhuzhenjie
 */
public class CastUtils {

    private CastUtils() {
    }

    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

}
