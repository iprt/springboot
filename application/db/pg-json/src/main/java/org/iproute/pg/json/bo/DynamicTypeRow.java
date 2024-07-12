package org.iproute.pg.json.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.pg.json.entities.po.Student;
import org.iproute.pg.json.entities.po.Teacher;

/**
 * GroupRow
 *
 * @author tech@intellij.io
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class DynamicTypeRow {

    private String tableName;
    private Long id;

    private Student stu;

    private Teacher teacher;

}
