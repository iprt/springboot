package org.iproute.mid.redis.cache.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.redis.cache.entities.Student;
import org.iproute.mid.redis.cache.repository.StuRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * StuServiceImpl
 *
 * @author zhuzhenjie
 * @since 3/15/2023
 */
@AllArgsConstructor
@Service
@Slf4j
public class StuServiceImpl implements StuService {
    private final StuRepository stuRepository;


    /**
     * CachePut 缓存新增的或更新的数据到缓存，其中缓存名称为 student 数据的 key 是 student 的 id
     *
     * @param student the student
     * @return the student
     */
    @CachePut(value = "student", key = "#student.id")
    @Override
    public Student saveStudent(Student student) {
        Student s = stuRepository.save(student);
        log.info("为id、key 为{}的数据做了缓存", s.getId());
        return s;
    }


    /**
     * CacheEvict 从缓存 student 中删除 key 为 id 的数据
     *
     * @param id the id
     */
    @CacheEvict(value = "student")
    @Override
    public void deleteStudentById(Integer id) {
        log.info("删除了id、key 为{}的数据缓存", id);
    }

    @Cacheable(value = "student", key = "#id")
    @Override
    public Student findStudentById(Integer id) {
        Student stu = stuRepository.findById(id).get();
        log.info("为id、key 为{}的数据做了缓存", id);
        return stu;
    }
}
