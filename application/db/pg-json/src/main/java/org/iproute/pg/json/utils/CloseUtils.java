package org.iproute.pg.json.utils;

/**
 * CloseUtils
 *
 * @author zhuzhenjie
 */
public class CloseUtils {
    private CloseUtils() {
    }

    public static void close(AutoCloseable... cs) {
        for (AutoCloseable c : cs) {
            try {
                c.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
