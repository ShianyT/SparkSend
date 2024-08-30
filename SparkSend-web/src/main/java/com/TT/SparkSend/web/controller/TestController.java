package com.TT.SparkSend.web.controller;

import com.TT.SparkSend.support.dao.MessageTemplateDao;
import com.TT.SparkSend.support.domain.MessageTemplate;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TestController {
    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @RequestMapping("/test")
    private String testLog(){
        log.info("log：日志测试输出");
        return "测试";
    }

    @RequestMapping("/testDatabase")
    private String testDatabase(){
        List<MessageTemplate> list = messageTemplateDao.findAllByIsDeletedEquals(0, PageRequest.of(0,10));
        return JSON.toJSONString(list);
    }

//    @RequestMapping("/testSend")
//    private String testSend(){
//
//    }

}
