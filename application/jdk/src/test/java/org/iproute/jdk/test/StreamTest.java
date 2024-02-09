package org.iproute.jdk.test;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StreamTest
 *
 * @author zhuzhenjie
 */
@Slf4j
public class StreamTest {

    @Test
    public void testGroup() {
        List<People> peoples = Lists.newArrayList(
                People.builder().id(1).name("name1").build(),
                People.builder().id(1).name("name11").build(),
                People.builder().id(2).name("name2").build(),
                People.builder().id(3).name("name3").build()
        );


        Map<Integer, List<String>> collect = peoples.stream().collect(
                Collectors.groupingBy(
                        People::getId,
                        Collectors.mapping(People::getName, Collectors.toList())
                )
        );

        collect.forEach((k, v) -> {
            log.info("key = {} , value = {}", k, v);
        });

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @Data
    public static class People {
        private int id;
        private String name;
    }

}
