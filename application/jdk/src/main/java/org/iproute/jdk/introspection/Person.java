package org.iproute.jdk.introspection;

import lombok.Data;

/**
 * Person
 *
 * @author tech@intellij.io
 * @since 5/22/2023
 */
@Data
public class Person {
    private long id;
    private String name;
    private int age;
    private double grade;
}