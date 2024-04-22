package org.iproute.pg.json.test;

import com.google.common.collect.Lists;
import org.iproute.pg.json.bo.GroupQueryParam;
import org.iproute.pg.json.bo.GroupRow;
import org.iproute.pg.json.entities.Student;
import org.iproute.pg.json.entities.Teacher;
import org.iproute.pg.json.mapper.DynamicMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DynamicTest
 *
 * @author devops@kubectl.net
 */
@SpringBootTest
public class DynamicTest {

    @Resource
    private DynamicMapper dynamicMapper;

    @Test
    public void testListStudents() {
        String tableName = "student";
        String tableAlias = "stu";

        GroupQueryParam param = GroupQueryParam.builder()
                .tableName(tableName).tableAlias(tableAlias)
                .colMappings(
                        Lists.newArrayList(
                                GroupQueryParam.ColMappings.builder().colName("id").colAlias("id").build(),
                                GroupQueryParam.ColMappings.builder().colName("student_name").colAlias("studentName").build(),
                                GroupQueryParam.ColMappings.builder().colName("create_time").colAlias("createTime").build()
                        )
                )
                .build();

        List<GroupRow> groupRows = dynamicMapper.dynamicSelect(param);

        List<Student> students = groupRows.stream().map(GroupRow::getStu).collect(Collectors.toList());
        students.forEach(System.out::println);

        List<Teacher> teachers = groupRows.stream().map(GroupRow::getTeacher)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Assertions.assertTrue(teachers.isEmpty());
        Assertions.assertFalse(students.isEmpty());
    }

    @Test
    public void testListTeachers() {
        String tableName = "teacher";
        String tableAlias = "teacher";

        GroupQueryParam param = GroupQueryParam.builder()
                .tableName(tableName).tableAlias(tableAlias)
                .colMappings(
                        Lists.newArrayList(
                                GroupQueryParam.ColMappings.builder().colName("id").colAlias("id").build(),
                                GroupQueryParam.ColMappings.builder().colName("teacher_name").colAlias("teacherName").build(),
                                GroupQueryParam.ColMappings.builder().colName("create_time").colAlias("createTime").build()
                        )
                )
                .build();

        List<GroupRow> groupRows = dynamicMapper.dynamicSelect(param);

        List<Student> students = groupRows.stream().map(GroupRow::getStu)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Teacher> teachers = groupRows.stream().map(GroupRow::getTeacher).collect(Collectors.toList());
        teachers.forEach(System.out::println);

        Assertions.assertFalse(teachers.isEmpty());
        Assertions.assertTrue(students.isEmpty());

    }


}
