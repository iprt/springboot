package org.iproute.springboot.design.mysqltree.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * JdbcTest
 *
 * @author zhuzhenjie
 * @since 2022/12/2
 */
public class JdbcTest {

    /**
     * Test allow multi queries.
     */
    @Test
    public void testAllowMultiQueries() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bugfix?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true",
                    "root", "root");

            Statement statement = conn.createStatement();

            statement.execute("update ubas_fusion_table\n" +
                    "set process_id = case process_id when 1 then 2 when 2 then 1 end\n" +
                    "where process_id in (1, 2);\n" +
                    "update ubas_fusion_table_field\n" +
                    "set process_id = case process_id when 1 then 2 when 2 then 1 end\n" +
                    "where process_id in (1, 2);\n" +
                    "update ubas_fusion_event\n" +
                    "set process_id = case process_id when 1 then 2 when 2 then 1 end\n" +
                    "where process_id in (1, 2);\n" +
                    "update ubas_fusion_property\n" +
                    "set process_id = case process_id when 1 then 2 when 2 then 1 end\n" +
                    "where process_id in (1, 2);");


            statement.close();

            conn.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Test kep map.
     */
    @Test
    public void testKepMap() {
        String str = "hello world";
        System.out.println(str);
    }

}
