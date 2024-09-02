package org.iproute.mid.redis.cache.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Student
 *
 * @author tech@intellij.io
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
