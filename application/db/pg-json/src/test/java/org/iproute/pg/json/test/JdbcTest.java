package org.iproute.pg.json.test;

import org.iproute.pg.json.utils.CloseUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JdbcTest
 *
 * @author zhuzhenjie
 */
public class JdbcTest {

    private static Connection conn;
    private static Statement stmt;

    @BeforeAll
    public static void init() throws Exception {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(
                "jdbc:postgresql://172.100.1.100:5432/pg_json",
                "postgres", "postgres"
        );

        stmt = conn.createStatement();

        conn.setAutoCommit(true);
        conn.setReadOnly(true);
    }

    @Test
    public void testSelect() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("select id ,detail,authors from book limit 10")) {
            while (rs.next()) {
                Object detail = rs.getObject("detail");
                System.out.println(detail.getClass());
            }
        }
    }

    @AfterAll
    public static void free() {
        CloseUtils.close(stmt, conn);
    }

}
