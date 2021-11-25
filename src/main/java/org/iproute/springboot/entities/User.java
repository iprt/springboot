package org.iproute.springboot.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * User
 *
 * @author winterfell
 * @since 2021/11/25
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("user")
public class User {
    @TableField("Host")
    private String host;

    @TableField("Name")
    private String name;
}
