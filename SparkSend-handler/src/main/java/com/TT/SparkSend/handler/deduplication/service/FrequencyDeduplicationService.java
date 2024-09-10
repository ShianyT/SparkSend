package com.TT.SparkSend.handler.deduplication.service;

import cn.hutool.core.text.StrPool;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.DeduplicationType;
import com.TT.SparkSend.handler.deduplication.limit.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Description 频次去重服务
 * @Author TT
 * @Date 2024/9/9
 */
public class FrequencyDeduplicationService extends AbstractDeduplicationService{

    private static final String PREFIX = "FRE";

    @Autowired
    public FrequencyDeduplicationService(@Qualifier("SimpleLimitService") LimitService limitService) {
        this.limitService = limitService;
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }


    /**
     * 业务规则去重 构建key <br>
     * key ： receiver + sendChannel <br>
     * 一天内一个用户只能收到某个渠道的消息 N 次
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrPool.C_UNDERLINE
                + receiver + StrPool.C_UNDERLINE
                + taskInfo.getSendChannel();
    }
}
