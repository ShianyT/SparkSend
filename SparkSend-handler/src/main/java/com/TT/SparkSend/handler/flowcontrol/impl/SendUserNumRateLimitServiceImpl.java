package com.TT.SparkSend.handler.flowcontrol.impl;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.enums.RateLimitStrategy;
import com.TT.SparkSend.handler.flowcontrol.FlowControlParam;
import com.TT.SparkSend.handler.flowcontrol.FlowControlService;
import com.TT.SparkSend.handler.flowcontrol.annotations.LocalRateLimit;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @Description 根据访问人数进行限流
 * @Author TT
 * @Date 2025/2/25
 */
// 将限流方式改为根据用户数限流
@LocalRateLimit(rateLimitStrategy = RateLimitStrategy.SEND_USER_NUM_RATE_LIMIT)
public class SendUserNumRateLimitServiceImpl implements FlowControlService {


    @Override
    public Double FlowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
        RateLimiter rateLimiter = flowControlParam.getRateLimiter();
        return rateLimiter.acquire(taskInfo.getReceiver().size());
    }
}
