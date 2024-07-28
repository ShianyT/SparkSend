package com.TT.SparkSend.service.api.service;

import com.TT.SparkSend.service.api.domain.BatchSendRequest;
import com.TT.SparkSend.service.api.domain.SendRequest;
import com.TT.SparkSend.service.api.domain.SendResponse;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/28
 */
public interface SendService {

    /**
     * 单条消息发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);

    /**
     * 多条消息批量发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}
