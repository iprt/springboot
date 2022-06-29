package org.iproute.springboot.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * JsonTest
 *
 * @author winterfell
 * @since 2022/6/29
 */
public class JsonTest {

    @Test
    public void readJson() throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("gx.json").getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\r\n");
        }

        String json = sb.toString();

        int size = ((JSONArray) JSONPath.eval(json, "features")).size();

        JSONObject attrs = (JSONObject) JSONPath.eval(sb.toString(), "features[0].attributes");

        List<String> attrList = attrs.keySet().stream().map(String::toLowerCase).collect(Collectors.toList());

        String tableName = "st_pipe_gx";


        for (int i = 0; i < size; i++) {

            StringBuilder sql = new StringBuilder("insert into ").append(tableName).append(" (")
                    .append(StringUtils.join(attrList, ","))
                    .append(",")
                    .append("start_lgtd").append(",")
                    .append("start_lttd").append(",")
                    .append("end_lgtd").append(",")
                    .append("end_lttd")
                    .append(")").append("\r\n")
                    .append("values");

            StringJoiner vj = new StringJoiner(",", "(", ")");

            int finalI = i;
            attrs.keySet().forEach(attr -> {
                vj.add(varchar(JSONPath.eval(json, attrPath(finalI, attr))));
            });

            vj.add(
                    varchar(
                            JSONPath.eval(json, geoPath(finalI, 0, 0))
                    )
            );

            vj.add(
                    varchar(
                            JSONPath.eval(json, geoPath(finalI, 0, 1))
                    )
            );

            vj.add(
                    varchar(
                            JSONPath.eval(json, geoPath(finalI, 1, 0))
                    )
            );

            vj.add(
                    varchar(
                            JSONPath.eval(json, geoPath(finalI, 1, 1))
                    )
            );

            sql.append(vj).append(";").append("\r\n");


            System.out.println(sql);
        }
        // System.out.println(sqlAll);
    }


    private String attrPath(int index, String name) {
        return "features[" + index + "].attributes." + name;
    }

    private String geoPath(int index, int a, int b) {
        return "features[" + index + "].geometry.paths[0][" + a + "][" + b + "]";
    }

    private String varchar(Object value) {
        if ("null".equalsIgnoreCase(String.valueOf(value))) {
            return "''";
        }
        return "'" + value + "'";
    }
}
