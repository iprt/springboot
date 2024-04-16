package org.iproute.mid.redis.cache.service;

import org.iproute.mid.redis.cache.entities.Student;

/**
 * StuService
 *
 * @author devops@kubectl.net
 * @since 3/15/2023
 */
public interface StuService {

    Student saveStudent(Student student);

    void deleteStudentById(Integer id);

    Student findStudentById(Integer id);

}
