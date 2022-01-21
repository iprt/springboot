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
public class RequestLogBean {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("application")
    private String application;

    @TableField("method")
    private String method;

    @TableField("uri")
    private String uri;

    @TableField("content_type")
    private String contentType;

    @TableField("query_string")
    private String queryString;

    /**
     * 预留
     */
    @TableField("body")
    private String body;

    @TableField("uname")
    private String uname;

    @TableField("uid")
    private long uid;

    @TableField("user_agent")
    private String userAgent;

    @TableField("ip")
    private String ip;

    @TableField("request_time")
    private Date requestTime;

    @TableField("request_desc")
    private String requestDesc;
}