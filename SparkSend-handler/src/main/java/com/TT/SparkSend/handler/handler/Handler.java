package com.TT.SparkSend.handler.handler;

import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;

/**
 * @Description 消息处理器
 * @Author TT
 * @Date 2024/9/11
 */
public interface Handler {
    /**
     * 处理器
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     */
    void recall(RecallTaskInfo recallTaskInfo);
}
