package com.TT.SparkSend.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.domain.SimpleTaskInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.dto.model.ContentModel;
import com.TT.SparkSend.common.enums.RespStatusEnum;
import com.TT.SparkSend.common.pipeline.BusinessProcess;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.service.api.impl.domain.SendTaskModel;
import com.TT.SparkSend.support.mq.SendMqService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 将消息发送到MQ，返回拼装好的messageId给到接口调用方
 * @Author TT
 * @Date 2024/9/5
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess<SendTaskModel> {
    @Autowired
    private SendMqService sendMqService;

    @Value("${SparkSend.business.topic.name}")
    private String sendMessageTopic;

    @Value("${SparkSend.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();
        try {
            String message = JSON.toJSONString(taskInfo, new SerializerFeature[]{SerializerFeature.WriteClassName});
            sendMqService.send(sendMessageTopic, message);
            context.setResponse(BasicResultVO.success(taskInfo.stream().map(v -> SimpleTaskInfo.builder()
                    .businessId(v.getBusinessId()).messageId(v.getMessageId()).bizId(v.getBizId()).build())
                    .collect(Collectors.toList())));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(CollUtil.getFirst(taskInfo.listIterator())));
        }
    }
}
