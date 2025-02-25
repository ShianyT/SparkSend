package com.TT.SparkSend.handler.action;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.pipeline.BusinessProcess;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.handler.handler.HandlerHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author TT
 * @Date 2025/2/25
 */
public class SendMessageAction implements BusinessProcess<TaskInfo> {
    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
    }
}
