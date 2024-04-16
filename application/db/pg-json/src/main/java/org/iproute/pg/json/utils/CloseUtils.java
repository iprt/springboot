package org.iproute.pg.json.utils;

/**
 * CloseUtils
 *
 * @author devops@kubectl.net
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
