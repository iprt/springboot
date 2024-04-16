package org.iproute.core.factory.xml;

/**
 * ConnectionFactory
 *
 * @author devops@kubectl.net
 */
public class ConnectionFactory {

    public Connection getConnection() {
        return new Connection("ConnectionFactory");
    }

}
