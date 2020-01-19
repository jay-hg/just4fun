package com.acai.just4fun.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Aspect
@Component
public class LogAOP {

    @Around(value = "execution(* com.acai.just4fun.controller.*.*(..)) && @annotation(requestMapping)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, PostMapping requestMapping) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            log.info("===类型：{}，参数：{}", arg.getClass().getTypeName(), arg);
        }

        Object returnValue = proceedingJoinPoint.proceed();
        return returnValue;
    }
}
