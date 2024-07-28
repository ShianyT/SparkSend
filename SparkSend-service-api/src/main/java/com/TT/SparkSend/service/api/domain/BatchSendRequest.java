package com.TT.SparkSend.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 多条消息批量发送接口参数
 * @Author TT
 * @Date 2024/7/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchSendRequest {
    /**
     * 执行业务类型
     */
    private String code;

    /**
     * 消息模板ID
     */
    private Long messageTemplatedId;

    /**
     * 消息参数列表
     */
    private List<MessageParam> messageParamList;

}
