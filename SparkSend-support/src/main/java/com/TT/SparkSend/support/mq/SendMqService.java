package com.TT.SparkSend.support.mq;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/1
 */
public interface SendMqService {
    /**
     * 发送消息
     */
    void send(String topic, String jsonValue);


}
