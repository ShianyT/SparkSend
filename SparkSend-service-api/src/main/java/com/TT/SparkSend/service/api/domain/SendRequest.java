package com.TT.SparkSend.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 单条消息发送/撤回接口参数
 * @Author TT
 * @Date 2024/7/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendRequest {

    /**
     * 执行业务类型
     * 必传,参考 BusinessCode枚举
     */
    private String code;


    /**
     * 消息相关的参数
     * 当业务类型为"send"，必传
     */
    private Long messageTemplateId;

    /**
     * 消息参数
     */
    private MessageParam messageParam;



}
