package org.iproute.mid.client.es.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class UserBean {
    private String name;
    private int age;
    private String sex;
}
