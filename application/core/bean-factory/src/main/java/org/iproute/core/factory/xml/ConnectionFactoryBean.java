package org.iproute.core.factory.xml;

import org.springframework.beans.factory.FactoryBean;

/**
 * ConnectionFactoryBean
 *
 * @author zhuzhenjie
 */
public class ConnectionFactoryBean implements FactoryBean<Connection> {
    @Override
    public Connection getObject() throws Exception {
        return new Connection("ConnectionFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }

    @Override
    public boolean isSingleton() {
        // return FactoryBean.super.isSingleton();
        return true;
    }
}
