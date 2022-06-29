package org.iproute.springboot.generic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

        JSONObject jo = JSON.parseObject(sb.toString());
        int size = jo.getJSONArray("features").size();

        jo.keySet().forEach(System.out::println);


        for (int i = 0; i < size; i++) {
            Object a = JSONPath.eval(sb.toString(), "$features[" + i + "].attributes.DZMS");
            System.out.println(a);
        }

    }

}
