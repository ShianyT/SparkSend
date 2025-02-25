package com.TT.SparkSend.handler.flowcontrol;

import com.TT.SparkSend.common.domain.TaskInfo;

/**
 * @Description 流量控制服务
 * @Author TT
 * @Date 2025/2/25
 */
public interface FlowControlService {


    /**
     * 根据渠道进行流量控制
     * @param taskInfo
     * @param flowControlParam
     * @return
     */
    Double FlowControl(TaskInfo taskInfo, FlowControlParam flowControlParam);
}
