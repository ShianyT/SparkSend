package com.TT.SprarkSend.stream.utils;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

/**
 * @Description 消息队列工具类
 * @Author TT
 * @Date 2025/2/25
 */
public class MessageQueueUtils {
    private MessageQueueUtils() {

    }

    /**
     * 获取KafkaSource
     * @param topicName
     * @param groupId
     * @param broker
     * @return
     */
    public static KafkaSource<String> getKafkaConsumer(String topicName, String groupId, String broker) {
        return KafkaSource.<String>builder()
                .setBootstrapServers(broker) // Kafka 服务器地址
                .setTopics(topicName) // 订阅的主题
                .setGroupId(groupId) // 消费者组id
                .setStartingOffsets(OffsetsInitializer.earliest()) // 从最早开始消费
                .setValueOnlyDeserializer(new SimpleStringSchema()) // Value 反序列化为字符串
                .build();
    }

}
