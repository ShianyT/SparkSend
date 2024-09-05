package com.TT.SparkSend.handler.action;

import com.TT.SparkSend.common.constant.CommonConstant;
import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.TT.SparkSend.common.pipeline.BusinessProcess;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.support.service.ConfigService;
import com.TT.SparkSend.support.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 丢弃消息：一般将需要丢弃的模板id写在分布式配置中心
 * @Author TT
 * @Date 2024/9/2
 */
@Service
public class DiscardAction implements BusinessProcess<TaskInfo> {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private ConfigService configService;
    @Autowired
    private LogUtils logUtils;


    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        // 读取丢弃配置，配置格式：["1","2"]
        JSONArray array = JSON.parseArray(configService.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            logUtils.print(AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId())
                    .businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            context.setNeedBreak(true);
        }
    }
}
