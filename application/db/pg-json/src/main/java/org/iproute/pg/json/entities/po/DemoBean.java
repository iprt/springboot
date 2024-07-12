package org.iproute.pg.json.entities.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Demo
 *
 * @author tech@intellij.io
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class DemoBean {

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "create_time")
    @JsonSerialize(using = DateToStringSerializer.class)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField(value = "update_time")
    private Date updateTime;

    @JacksonStdImpl
    public static class DateToStringSerializer extends StdSerializer<Date> {

        // 关键点 无参构造器
        protected DateToStringSerializer() {
            super(Date.class);
        }

        @Override
        public void serialize(Date date, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
            if (Objects.nonNull(date)) {
                gen.writeString(
                        new SimpleDateFormat("yyyy-MM-dd").format(date)
                );
            } else {
                gen.writeString("");
            }
        }

    }

}
