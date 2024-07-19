package com.SparkSend.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String test(){
        log.info("log：日志测试输出");
        return "测试";
    }
}
