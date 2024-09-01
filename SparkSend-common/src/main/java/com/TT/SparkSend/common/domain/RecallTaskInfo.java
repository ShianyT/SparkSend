package com.TT.SparkSend.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 撤回任务消息
 * @Author TT
 * @Date 2024/9/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecallTaskInfo {
    /**
     * 消息模板Id
     */
    private Long messageTemplateId;

    /**
     * 需要撤回的消息ids
     * （有传入消息ids时，优先撤回dis）
     */
    private List<String> recallMessageId;

    /**
     * 发送账号
     */
    private Integer sendAccount;

    /**
     * 发送渠道
     */
    private Integer sendChannel;
}
