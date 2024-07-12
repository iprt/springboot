package org.iproute.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.repository.springboot.RequestLogBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

/**
 * ReqLogController
 *
 * @author tech@intellij.io
 * @since 2023/7/31
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/reqLog")
@RestController
@Slf4j
public class ReqLogController {
    private final RequestLogBeanMapper requestLogBeanMapper;

    @GetMapping("/listAll")
    public List<RequestLogBean> listAll() {
        return requestLogBeanMapper.selectList(null);
    }

    @GetMapping("/listAll/2023")
    public List<RequestLogBean> listAll2023() {
        LocalDate start = LocalDate.of(2023, Month.JANUARY, 1);

        LocalDateTime end = LocalDateTime.of(2023, Month.DECEMBER, 31, 23, 59, 59);

        return requestLogBeanMapper.selectList(Wrappers.<RequestLogBean>lambdaQuery()
                .ge(RequestLogBean::getRequestTime, Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .lt(RequestLogBean::getRequestTime, Date.from(end.atZone(ZoneId.systemDefault()).toInstant()))
        );
    }

}
