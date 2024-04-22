package org.iproute.pg.json.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.pg.json.entities.Student;
import org.iproute.pg.json.entities.Teacher;

/**
 * GroupRow
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class GroupRow {

    private Student stu;

    private Teacher teacher;

}
