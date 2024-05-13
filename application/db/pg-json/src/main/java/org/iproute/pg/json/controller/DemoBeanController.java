package org.iproute.pg.json.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.pg.json.entities.po.DemoBean;
import org.iproute.pg.json.mapper.DemoBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * DemoBeanController
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/")
@Slf4j
public class DemoBeanController {
    private final DemoBeanMapper demoBeanMapper;

    @GetMapping("/addDemoBean")
    public int addDemoBean() {
        return demoBeanMapper.insert(
                DemoBean.builder().name(UUID.randomUUID().toString()).build()
        );
    }

    @GetMapping("/addDemoBeanWithTransaction")
    @Transactional(rollbackFor = Exception.class)
    public boolean addDemoBeanWithTransaction(@RequestBody Map<String, Integer> request) {
        final Integer count = request.get("count");
        if (Objects.isNull(count) || count < 1) {
            return false;
        }

        int added = IntStream.range(1, count + 1)
                .map(c -> {
                    if (c % 2 == 1) {
                        return demoBeanMapper.insert(
                                DemoBean.builder().name(c + "_" + UUID.randomUUID()).build()
                        );
                    } else {
                        return 0;
                    }
                })
                .sum();

        if (count != added) {
            log.error("insert occurred error");
            throw new RuntimeException("insert occurred error");
        }
        return true;
    }

    @GetMapping("/listDemos")
    public List<DemoBean> listDemos() {
        return demoBeanMapper.selectList(null);
    }

}
