package org.iproute.springboot.entities.po.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * MysqlUser
 *
 * @author zhuzhenjie
 * @since 2022/7/19
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("user")
public class MysqlUser {

    private String user;

    private String host;

    private String plugin;

    private String authenticationString;

    private String passwordExpired;

    private Date passwordLastChanged;

    private String accountLocked;
}
