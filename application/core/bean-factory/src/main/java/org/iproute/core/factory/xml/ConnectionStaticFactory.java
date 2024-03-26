package org.iproute.core.factory.xml;

/**
 * ConnectionStaticFactory
 *
 * @author zhuzhenjie
 */
public class ConnectionStaticFactory {

    public static Connection getConnection() {
        return new Connection("ConnectionStaticFactory");
    }
}
