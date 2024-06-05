package org.iproute.pg.json.entities.po;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DemoBeanLeaf
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DemoBeanLeaf {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @Override
    public String toString() {
        try {
            return JSON.toJSONString(this);
        } catch (Exception e) {
            return e.toString();
        }
    }
}
