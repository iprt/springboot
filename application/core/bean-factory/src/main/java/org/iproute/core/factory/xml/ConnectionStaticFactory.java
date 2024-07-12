package org.iproute.core.factory.xml;

/**
 * ConnectionStaticFactory
 *
 * @author tech@intellij.io
 */
public class ConnectionStaticFactory {

    public static Connection getConnection() {
        return new Connection("ConnectionStaticFactory");
    }
}
