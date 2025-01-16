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

/**
 * User
 *
 * @author tech@intellij.io
 * @since 2025-01-15
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName(value = "demo_user")
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "age")
    private int age;

    @TableField(value = "email")
    private String email;

}
