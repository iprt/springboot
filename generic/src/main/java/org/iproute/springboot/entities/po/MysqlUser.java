package org.iproute.springboot.entities.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

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
public class MysqlUser {
    @TableField("host")
    private String host;

    @TableField("User")
    private String user;

    @TableField("plugin")
    private String plugin;

    @TableField("password_last_changed")
    private LocalDateTime passwordLastChanged;
}
