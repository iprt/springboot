package org.iproute.springboot.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * ControllerAop
 *
 * @author devops@kubectl.net
 * @since 2021/11/25
 */
@Component
@Aspect
@Slf4j
public class AccessAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingPointcut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMappingPoint() {
    }

    @Around("getMappingPointcut()")
    public Object aroundGetMapping(ProceedingJoinPoint joinPoint) throws Throwable {
        return statistic(joinPoint);
    }

    @Around("postMappingPoint()")
    public Object aroundPostMapping(ProceedingJoinPoint joinPoint) throws Throwable {
        return statistic(joinPoint);
    }

    private Object statistic(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String name = joinPoint.getTarget().getClass().getSimpleName();
        String functionName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        Object res = null;
        try {
            res = joinPoint.proceed();
        } finally {
            long cost = System.currentTimeMillis() - start;
            // log.info("\r\n\tControllerName: {}\r\n\tFunctionName: {}\r\n\tCostTime: {}ms", name, functionName, cost);
            log.info("\n    controller name : {} | function name : {} | cost time : {}ms", name, functionName, cost);
        }
        return res;
    }
}
