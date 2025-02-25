package com.TT.SprarkSend.stream.constants;

/**
 * @Description Flink常量信息
 * @Author TT
 * @Date 2025/2/25
 */
public class SparkSendFlinkConstant {
    /**
     * Kafka 配置信息
     */
    public static final String GROUP_ID = "sparksendLogGroup";
    public static final String TOPIC_NAME = "sparksendTraceLog";
    public static final String BROKER = "sparksend-kafka:9092";
    /**
     * redis 配置
     */
    public static final String REDIS_IP = "sparksend-redis";
    public static final String REDIS_PORT = "6379";
    public static final String REDIS_PASSWORD = "sparksend";
    /**
     * Flink流程常量
     */
    public static final String SOURCE_NAME = "sparksend_kafka_source";
    public static final String FUNCTION_NAME = "sparksend_transfer";
    public static final String SINK_NAME = "sparksend_sink";
    public static final String JOB_NAME = "SparkSendBootStrap";
    private SparkSendFlinkConstant() {
    }
}
