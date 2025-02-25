package com.TT.SparkSend.handler.flowcontrol.impl;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.flowcontrol.FlowControlParam;
import com.TT.SparkSend.handler.flowcontrol.FlowControlService;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @Description 根据请求速率进行限流
 * @Author TT
 * @Date 2025/2/25
 */
public class RequestRateLimitServiceImpl implements FlowControlService {
    /**
     * 根据
     * @param taskInfo
     * @param flowControlParam
     * @return
     */
    @Override
    public Double FlowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
        RateLimiter rateLimiter = flowControlParam.getRateLimiter();
        return rateLimiter.acquire(1);
    }
}
