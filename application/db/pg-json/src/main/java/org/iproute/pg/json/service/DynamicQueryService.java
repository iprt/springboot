package org.iproute.pg.json.service;

import org.iproute.pg.json.entities.dto.TypeEnum;
import org.iproute.pg.json.entities.po.Student;
import org.iproute.pg.json.entities.po.Teacher;

import java.util.List;

/**
 * DynamicQueryService
 *
 * @author tech@intellij.io
 */
public interface DynamicQueryService {

    List<Student> getStudents(TypeEnum stuEnum);

    List<Teacher> getTeachers(TypeEnum tecEnum);

}
