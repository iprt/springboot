package org.iproute.mid.redis.cache.controller;

import lombok.AllArgsConstructor;
import org.iproute.mid.redis.cache.entities.Student;
import org.iproute.mid.redis.cache.service.StuService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StuController
 *
 * @author tech@intellij.io
 * @since 3/15/2023
 */
@RestController
@RequestMapping("/stu")
@AllArgsConstructor
public class StuController {

    private final StuService stuService;

    @PostMapping("/put")
    public Student saveStudent(@RequestBody Student student) {
        return stuService.saveStudent(student);
    }

    @DeleteMapping("/evit/{id}")
    public void deleteStudentById(@PathVariable("id") Integer id) {
        stuService.deleteStudentById(id);
    }

    @GetMapping("/able/{id}")
    public Student findStudentById(@PathVariable("id") Integer id) {
        return stuService.findStudentById(id);
    }


}
