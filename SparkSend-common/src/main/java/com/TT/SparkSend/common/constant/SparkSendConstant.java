package com.TT.SparkSend.common.constant;

/**
 * @Description SparkSend常量信息
 * @Author TT
 * @Date 2025/2/25
 */
public class SparkSendConstant {

    /**
     * businessId默认的长度
     * 生成的逻辑：com.java3y.austin.support.utils.TaskInfoUtils#generateBusinessId(java.lang.Long, java.lang.Integer)
     */
    public static final Integer BUSINESS_ID_LENGTH = 16;
    /**
     * 接口限制 最多的人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100;

    /**
     * 链路追踪缓存的key标识
     */
    public static final String CACHE_KEY_PREFIX = "SparkSend";
    public static final String MESSAGE_ID = "MessageId";

    private SparkSendConstant() {

    }
}
