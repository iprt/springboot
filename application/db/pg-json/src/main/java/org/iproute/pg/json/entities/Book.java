package org.iproute.pg.json.entities;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Book
 *
 * @author zhuzhenjie
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName(value = "book", autoResultMap = true)
public class Book {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    @TableField(value = "detail", typeHandler = DetailTypeHandler.class)
    private Detail detail;

    @TableField(value = "authors", typeHandler = ListAuthorTypeHandler.class)
    private List<Author> authors;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @Data
    public static class Detail {
        private String title;
        private String remark;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date createTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date updateTime;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @Data
    public static class Author {
        private String authorName;
        private int age;
    }

    public static class DetailTypeHandler extends BaseTypeHandler<Detail> {
        PGobject jsonObject = new PGobject();

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, Detail parameter, JdbcType jdbcType) throws SQLException {
            jsonObject.setType("json");
            jsonObject.setValue(this.toJson(parameter));
            ps.setObject(i, jsonObject);
        }

        @Override
        public Detail getNullableResult(ResultSet rs, String columnName) throws SQLException {
            return this.toObj(
                    rs.getString(columnName)
            );
        }

        @Override
        public Detail getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            return this.toObj(
                    rs.getString(columnIndex)
            );
        }

        @Override
        public Detail getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            return this.toObj(
                    cs.getString(columnIndex)
            );
        }

        private String toJson(Detail detail) {
            if (detail == null) {
                return null;
            }
            return JSON.toJSONString(detail);
        }

        private Detail toObj(String json) {
            if (StringUtils.isBlank(json)) {
                return null;
            }
            try {
                return JSON.parseObject(json, Detail.class);
            } catch (Exception e) {
                return null;
            }
        }

    }

    public static class ListAuthorTypeHandler extends BaseTypeHandler<List<Author>> {

        PGobject jsonObject = new PGobject();

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, List<Author> parameter, JdbcType jdbcType) throws SQLException {
            jsonObject.setType("json");
            jsonObject.setValue(this.toJson(parameter));
            ps.setObject(i, jsonObject);
        }

        @Override
        public List<Author> getNullableResult(ResultSet rs, String columnName) throws SQLException {
            return this.toList(
                    rs.getString(columnName)
            );
        }

        @Override
        public List<Author> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            return this.toList(
                    rs.getString(columnIndex)
            );
        }

        @Override
        public List<Author> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            return this.toList(
                    cs.getString(columnIndex)
            );
        }

        private String toJson(List<Author> authors) {
            if (authors == null) {
                return null;
            }
            return JSON.toJSONString(authors);
        }

        private List<Author> toList(String json) {
            if (StringUtils.isBlank(json)) {
                return new ArrayList<>();
            }
            try {
                return JSON.parseArray(json, Author.class);
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }

    }
}
