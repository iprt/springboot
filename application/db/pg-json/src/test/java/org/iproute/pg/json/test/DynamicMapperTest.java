package org.iproute.pg.json.test;

import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.bo.DynamicTypeRow;
import org.iproute.pg.json.entities.po.Student;
import org.iproute.pg.json.entities.po.Teacher;
import org.iproute.pg.json.mapper.DynamicMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.iproute.pg.json.bo.DynamicQueryParam.queryStu;
import static org.iproute.pg.json.bo.DynamicQueryParam.queryTeacher;

/**
 * DynamicTest
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class DynamicMapperTest {

    private final DynamicMapper dynamicMapper;

    @Test
    public void testListStudents() {
        List<DynamicTypeRow> dynamicTypeRows = dynamicMapper.dynamicSelect(queryStu);

        List<Student> students = dynamicTypeRows.stream().map(DynamicTypeRow::getStu).collect(Collectors.toList());
        students.forEach(System.out::println);

        List<Teacher> teachers = dynamicTypeRows.stream().map(DynamicTypeRow::getTeacher)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Assertions.assertTrue(teachers.isEmpty());
        Assertions.assertFalse(students.isEmpty());
    }

    @Test
    public void testListTeachers() {
        List<DynamicTypeRow> dynamicTypeRows = dynamicMapper.dynamicSelect(queryTeacher);

        List<Student> students = dynamicTypeRows.stream().map(DynamicTypeRow::getStu)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Teacher> teachers = dynamicTypeRows.stream().map(DynamicTypeRow::getTeacher).collect(Collectors.toList());
        teachers.forEach(System.out::println);

        Assertions.assertFalse(teachers.isEmpty());
        Assertions.assertTrue(students.isEmpty());

    }


}
