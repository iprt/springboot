package org.iproute.jdk.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * DemoBeanExtend
 *
 * @author devops@kubectl.net
 * @since 2023/8/2
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DemoBeanExtend extends DemoBean {

    private String random;

}
