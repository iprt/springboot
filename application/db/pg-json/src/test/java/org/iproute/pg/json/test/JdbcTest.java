package org.iproute.pg.json.test;

import org.iproute.pg.json.utils.CloseUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JdbcTest
 *
 * @author tech@intellij.io
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
    public void testSelect() {
        try (ResultSet rs = stmt.executeQuery(
                "select id ,detail,authors,types from book limit 10"
        )) {
            while (rs.next()) {
                Object detail = rs.getObject("detail");
                Object authors = rs.getObject("authors");
                Array array = rs.getArray("types");
                System.out.println("detail:" + detail.getClass() + " | authors:" + authors.getClass() + " | types:" + array.getClass());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void free() {
        CloseUtils.close(stmt, conn);
    }

}
