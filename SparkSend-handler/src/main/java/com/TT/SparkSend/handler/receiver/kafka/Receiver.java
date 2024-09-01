package com.TT.SparkSend.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.receiver.MessageReceiver;

import com.TT.SparkSend.handler.service.ConsumeService;
import com.TT.SparkSend.handler.utils.GroupIdMappingUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Description 消费MQ的消息
 * @Author TT
 * @Date 2024/9/1
 */
@Slf4j
@Component
/*
Spring IOC 容器中的一个作用域，共有四种作用域：
    1、singleton     单实例的(单例)(默认)    ----全局有且仅有一个实例
    2、prototype     多实例的(多例)  ---- 每次获取Bean的时候会有一个新的实例
    3、request       同一次请求     ---- request：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
    4、session       同一个会话级别  ---- session：每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Receiver implements MessageReceiver {
    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     */
    @KafkaListener(topics = "#{'${SparkSend.business.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            List<TaskInfo> taskInfoList = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoList.iterator()));
            // 每个消费者组只消费他们自身关心的消息
            if(topicGroupId.equals(messageGroupId)){
                consumeService.consume2Send(taskInfoList);
            }
        }
    }

}
