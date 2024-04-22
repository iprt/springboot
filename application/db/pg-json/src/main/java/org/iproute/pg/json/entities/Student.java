package org.iproute.pg.json.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Student
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName(value = "student")
public class Student {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "student_name")
    private String studentName;

    @TableField(value = "create_time")
    private Date createTime;

}
