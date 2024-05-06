package org.iproute.pg.json.service.impl;

import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.bo.DynamicQueryParam;
import org.iproute.pg.json.entities.dto.TypeEnum;
import org.iproute.pg.json.entities.po.Student;
import org.iproute.pg.json.entities.po.Teacher;
import org.iproute.pg.json.mapper.DynamicMapper;
import org.iproute.pg.json.service.DynamicQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DynamicQueryServiceImpl
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DynamicQueryServiceImpl implements DynamicQueryService {
    private final DynamicMapper dynamicMapper;

    @Override
    public List<Student> getStudents(TypeEnum stuEnum) {
        return dynamicMapper.dynamicSelect(DynamicQueryParam.queryStu)
                .stream()
                .map(row -> stuEnum.getGetter().apply(row))
                .map(obj -> (Student) obj)
                .collect(Collectors.toList());
    }

    @Override
    public List<Teacher> getTeachers(TypeEnum tecEnum) {
        return dynamicMapper.dynamicSelect(DynamicQueryParam.queryTeacher)
                .stream()
                .map(row -> tecEnum.getGetter().apply(row))
                .map(obj -> (Teacher) obj)
                .collect(Collectors.toList());
    }

}
