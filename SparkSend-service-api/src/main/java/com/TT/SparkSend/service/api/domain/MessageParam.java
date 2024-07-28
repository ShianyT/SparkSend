package com.TT.SparkSend.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description 消息参数
 * @Author TT
 * @Date 2024/7/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageParam {

    /**
     * 业务消息发送Id, 用于链路追踪, 若不存在, austin 则生成一个消息Id
     */
    private String bizId;

    /**
     * 接收者
     * 多个用,逗号号分隔开
     * 【不能大于100个】
     * 必传
     */
    private String receiver;

    /**
     * 消息内容中的可变部分(占位符替换)
     * 可选
     */
    private Map<String, String> variables;

    /**
     * 扩展参数
     * 可选
     */
    private Map<String, String> extra;
}
