package com.TT.SparkSend.handler.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.LogParam;
import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.TT.SparkSend.handler.pending.Task;
import com.TT.SparkSend.handler.pending.TaskPendingHolder;
import com.TT.SparkSend.handler.service.ConsumeService;
import com.TT.SparkSend.handler.utils.GroupIdMappingUtils;
import com.TT.SparkSend.support.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/1
 */
@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Autowired
    private LogUtils logUtils;


    @Override
    public void consume2Send(List<TaskInfo> taskInfoList) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoList.iterator()));
        for (TaskInfo taskInfo : taskInfoList) {
            logUtils.print(LogParam.builder().bizType(taskInfo.getBizId()).object(taskInfo).build(), AnchorInfo
                    .builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).ids(taskInfo.getReceiver())
                    .businessId(taskInfo.getBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }

    @Override
    public void consume2recall(RecallTaskInfo recallTaskInfo) {

    }
}
