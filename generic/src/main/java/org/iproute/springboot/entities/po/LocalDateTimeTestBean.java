package org.iproute.springboot.entities.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * LocalTimeTestBean
 *
 * @author winterfell
 * @since 2022/1/23
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("localtime_test")
public class LocalDateTimeTestBean {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("create_time")
    private Date createTime;
}
