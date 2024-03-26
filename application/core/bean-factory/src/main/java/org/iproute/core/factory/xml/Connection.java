package org.iproute.core.factory.xml;

/**
 * Connection
 *
 * @author zhuzhenjie
 */
public class Connection {

    private final String createBy;

    public Connection(String createBy) {
        this.createBy = createBy;
    }

    public void execute(String sql) {
        System.out.println("this.createBy = " + this.createBy);
        System.out.println("Connection.execute: " + sql);
    }

}

