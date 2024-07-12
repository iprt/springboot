package org.iproute.mid.redis.cache.repository;

import org.iproute.mid.redis.cache.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * StuRepository
 *
 * @author tech@intellij.io
 * @since 3/15/2023
 */
@Repository
public interface StuRepository extends JpaRepository<Student, Integer> {

}
