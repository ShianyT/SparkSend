package com.TT.SparkSend.support.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.LogParam;
import com.TT.SparkSend.support.mq.SendMqService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 所有的日志都存在
 * @Author TT
 * @Date 2024/9/2
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {

    @Autowired
    private SendMqService sendMqService;

    @Value("${SparkSend.business.log.topic.name}")
    private String topicName;

    /**
     * 方法切面的日志 @OperationLog所产生
     */
    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        log.info(JSON.toJSONString(logDTO));
    }

    /**
     * 记录当前对象信息
     */
    public void print(LogParam logParam) {
        logParam.setTimestamp(System.currentTimeMillis());
        log.info(JSON.toJSONString(logParam));
    }

    /**
     * 记录打点信息
     */
    public void print(AnchorInfo anchorInfo) {
        anchorInfo.setLogTimestamp(System.currentTimeMillis());
        String message = JSON.toJSONString((anchorInfo));
        log.info(message);

        try {
            sendMqService.send(topicName, message);
        } catch (Exception e) {
            log.error("LogUtils#print send mq fail! e:{},params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(anchorInfo));
        }
    }

    /**
     * 记录当前对象信息和打点信息
     */
    public void print(LogParam logParam, AnchorInfo anchorInfo) {
        print(logParam);
        print(anchorInfo);
    }

}
