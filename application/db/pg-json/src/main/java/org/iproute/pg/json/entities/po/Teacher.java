package org.iproute.pg.json.entities.po;

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
 * Teacher
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName(value = "teacher")
public class Teacher {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "teacher_name")
    private String teacherName;

    @TableField(value = "create_time")
    private Date createTime;
}
