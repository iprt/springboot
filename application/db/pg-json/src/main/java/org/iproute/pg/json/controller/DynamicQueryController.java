package org.iproute.pg.json.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.pg.json.entities.dto.TypeDTO;
import org.iproute.pg.json.entities.po.Student;
import org.iproute.pg.json.entities.po.Teacher;
import org.iproute.pg.json.service.DynamicQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DynamicQueryController
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class DynamicQueryController {
    private final DynamicQueryService queryService;

    @PostMapping("/listStudent")
    public List<Student> listStudent(@RequestBody TypeDTO typeDTO) {
        return queryService.getStudents(typeDTO.getType());
    }

    @PostMapping("/listTeacher")
    public List<Teacher> listTeacher(@RequestBody TypeDTO typeDTO) {
        return queryService.getTeachers(typeDTO.getType());
    }

}
