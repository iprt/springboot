package org.iproute.core.factory.xml;

/**
 * ConnectionFactory
 *
 * @author zhuzhenjie
 */
public class ConnectionFactory {

    public Connection getConnection() {
        return new Connection("ConnectionFactory");
    }

}
