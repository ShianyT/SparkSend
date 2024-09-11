package com.TT.SparkSend.handler.handler.impl;

import com.TT.SparkSend.common.domain.RecallTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.dto.model.EmailContentModel;
import com.TT.SparkSend.common.enums.ChannelType;
import com.TT.SparkSend.handler.handler.BaseHandler;
import com.TT.SparkSend.support.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description 邮件发送处理
 * @Author TT
 * @Date 2024/9/11
 */
public class EmailHandler extends BaseHandler {

    @Autowired
    private AccountUtils accountUtils;

    // @Value("")

    public EmailHandler() {
        channelCode = ChannelType.EMAIL.getCode();
        // TODO 限流操作
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        return true;
    }

    @Override
    public void recall(RecallTaskInfo recallTaskInfo) {

    }
}
