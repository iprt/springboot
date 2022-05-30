package org.iproute.springboot.design.tdengine.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Demo
 *
 * @author zhuzhenjie
 * @since 2022/5/30
 */
@TableName("demo")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Demo {
    private Date ts;
    private String nickname;
}
