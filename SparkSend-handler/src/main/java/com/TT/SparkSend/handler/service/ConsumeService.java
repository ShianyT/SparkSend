package com.TT.SparkSend.handler.service;

import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;

import java.util.List;

/**
 * @Description 消费消息服务
 * @Author TT
 * @Date 2024/9/1
 */
public interface ConsumeService {
    /**
     * 从MQ拉到消息进行消费，发送消息
     */
    void consume2Send(List<TaskInfo> taskInfoList);

    /**
     * 从MQ拉到消息进行消费，撤回消息
     * 如果有recallMessageId，则优先撤回recallMessageId
     * 如果没有，则撤回整个模板的消息
     */
    void consume2recall(RecallTaskInfo recallTaskInfo);
}
