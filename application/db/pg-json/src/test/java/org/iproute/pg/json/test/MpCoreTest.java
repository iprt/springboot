package org.iproute.pg.json.test;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.entities.po.User;
import org.iproute.pg.json.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MpCoreTest
 *
 * @author tech@intellij.io
 * @since 2025-01-15
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class MpCoreTest {

    private final UserMapper userMapper;

    @Test
    public void testTableInfo() {
        // tips: 如果没有 BaseMapper TableInfoHelper 是获取不到 TableInfo 的
        //     所以从源码追踪的角度来看，需要追踪 TableInfoHelper 的初始化过程

        TableInfo tableInfo = TableInfoHelper.getTableInfo(User.class);
        System.out.println("tableInfo = " + tableInfo);

    }

    @Test
    public void testBaseMapper() {
        userMapper.selectList(null).forEach(System.out::println);

        TableInfoHelper.getTableInfos().forEach(tableInfo -> {
            String tableName = tableInfo.getTableName();
            System.out.println("tableName = " + tableName);

            if ("demo_user".equals(tableName)) {
                System.out.println("tableInfo = " + tableInfo);
            }

        });
    }
}
