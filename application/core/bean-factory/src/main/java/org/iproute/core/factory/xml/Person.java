package org.iproute.core.factory.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Person
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Person {
    private String name;
    private int age;
}
