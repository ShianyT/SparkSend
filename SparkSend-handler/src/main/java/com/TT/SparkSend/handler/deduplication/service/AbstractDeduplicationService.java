package com.TT.SparkSend.handler.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.deduplication.DeduplicationHolder;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;
import com.TT.SparkSend.handler.deduplication.limit.LimitService;
import com.TT.SparkSend.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public abstract class AbstractDeduplicationService implements DeduplicationService{

    protected Integer deduplicationType;

    protected LimitService limitService;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Autowired
    private LogUtils logUtils;

    @PostConstruct
    public void init() {
        deduplicationHolder.putService(deduplicationType, this);
    }

    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();

        Set<String> filterReceiver = limitService.limitFilter(this, taskInfo, param);

        // 剔除符合去重条件的用户
        if (CollUtil.isNotEmpty(filterReceiver)) {
            taskInfo.getReceiver().remove(filterReceiver);
            logUtils.print(AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId())
                    .businessId(taskInfo.getBusinessId()).ids(filterReceiver)
                    .state(param.getAnchorState().getCode()).build());

        }
    }

    /**
     * 构建去重的key
     */
    public abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);
}
