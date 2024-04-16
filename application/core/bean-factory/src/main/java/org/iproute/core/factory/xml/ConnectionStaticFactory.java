package org.iproute.core.factory.xml;

/**
 * ConnectionStaticFactory
 *
 * @author devops@kubectl.net
 */
public class ConnectionStaticFactory {

    public static Connection getConnection() {
        return new Connection("ConnectionStaticFactory");
    }
}
