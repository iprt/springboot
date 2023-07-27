package org.iproute.springboot.design.tdengine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Demo
 *
 * @author zhuzhenjie
 * @since 2022/5/30
 */
@TableName("demo")
@ToString
@Data
public class Demo {
    private Date ts;
    private String nickname;
}
