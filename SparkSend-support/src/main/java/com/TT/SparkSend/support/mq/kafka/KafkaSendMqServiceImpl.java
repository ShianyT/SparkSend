package com.TT.SparkSend.support.mq.kafka;

import com.TT.SparkSend.support.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description kafka发送消息实现类
 * @Author TT
 * @Date 2024/9/1
 */
@Slf4j
@Service
public class KafkaSendMqServiceImpl implements SendMqService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void send(String topic, String jsonValue) {
        kafkaTemplate.send(topic, jsonValue);
    }
}
