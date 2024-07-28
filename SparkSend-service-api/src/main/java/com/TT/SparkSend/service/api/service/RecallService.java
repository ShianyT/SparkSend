package com.TT.SparkSend.service.api.service;

import com.TT.SparkSend.service.api.domain.SendRequest;
import com.TT.SparkSend.service.api.domain.SendResponse;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/28
 */
public interface RecallService {

    /**
     * 消息下发撤回接口
     * 根据 模板ID 或消息id 撤回消息
     * 如果只传入 messageTemplateId，则会撤回整个模板下发的消息
     * 如果还传入 recallMessageId，则优先撤回该 ids 的消息
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
