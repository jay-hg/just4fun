package com.acai.just4fun.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Aspect
@Component
public class LogAOP {

    @Before(value = "execution(* com.acai.just4fun.controller.*.*(..)) && @annotation(requestMapping)")
    public void log(PostMapping requestMapping) {
        log.info("一个请求进来啦...");
    }
}
