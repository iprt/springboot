package org.iproute.core.factory.xml;

/**
 * ConnectionFactory
 *
 * @author tech@intellij.io
 */
public class ConnectionFactory {

    public Connection getConnection() {
        return new Connection("ConnectionFactory");
    }

}
