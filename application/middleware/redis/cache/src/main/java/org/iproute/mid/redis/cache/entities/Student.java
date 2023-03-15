package org.iproute.mid.redis.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Student
 *
 * @author zhuzhenjie
 * @since 3/15/2023
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer age;
}
