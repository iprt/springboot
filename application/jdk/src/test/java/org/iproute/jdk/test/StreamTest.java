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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * StreamTest
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class StreamTest {

    @Test
    public void testGroupWithProperty() {
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

    @Test
    public void testGroup() {

        List<People> peoples = Lists.newArrayList(
                People.builder().id(1).name("name_1").build(),
                People.builder().id(1).name("name-1").build(),
                People.builder().id(2).name("name_2").build()
        );


        Map<Integer, People> aMap = peoples.stream().collect(
                Collectors.toMap(
                        People::getId, Function.identity(), (a, b) -> a
                )
        );

        Map<Integer, People> bMap = peoples.stream().collect(
                Collectors.toMap(
                        People::getId, Function.identity(), (a, b) -> b
                )
        );


        log.info("a map");

        aMap.forEach((k, v) ->
                log.info("key = {} , value = {}", k, v)
        );

        log.info("b map");

        bMap.forEach((Integer k, People v) ->
                log.info("key = {} , value = {}", k, v)
        );

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
