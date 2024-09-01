package com.TT.SparkSend.handler.service.impl;

import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.service.ConsumeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/1
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {


    @Override
    public void consume2Send(List<TaskInfo> taskInfoList) {

    }

    @Override
    public void consume2recall(RecallTaskInfo recallTaskInfo) {

    }
}
