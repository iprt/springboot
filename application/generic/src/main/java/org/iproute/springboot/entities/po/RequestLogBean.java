package org.iproute.springboot.entities.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * RequestLog
 *
 * @author zhuzhenjie
 * @since 2021/11/25
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("request_log")
public class RequestLogBean {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("request_time")
    private Date requestTime;

    @TableField("application")
    private String application;

    @TableField("uname")
    private String uname;

    @TableField("uid")
    private long uid;

    @TableField("uri")
    private String uri;

    @TableField("success")
    private Boolean success;

    @TableField("method")
    private String method;

    @TableField("content_type")
    private String contentType;

    @TableField("request_desc")
    private String requestDesc;


    @TableField("query_string")
    private String queryString;

    /**
     * 预留
     */
    @TableField("body")
    private String body;

    @TableField("ip")
    private String ip;

    @TableField("user_agent")
    private String userAgent;

}