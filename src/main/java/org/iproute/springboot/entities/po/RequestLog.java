package org.iproute.springboot.entities.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * RequestLog
 *
 * @author winterfell
 * @since 2021/11/25
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("request_log")
public class RequestLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("application")
    private String application;
    @TableField("uri")
    private String uri;
    @TableField("create_time")
    private Date createTime;
}
