package com.TT.SparkSend.service.api.impl.domain;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.service.api.domain.MessageParam;
import com.TT.SparkSend.support.domain.MessageTemplate;
import com.TT.SparkSend.common.pipeline.ProcessModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {

    /**
     * 消息模板ID
     */
    private Long messageTemplateId;

    /**
     * 消息请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务信息
     */
    private List<TaskInfo> taskInfo;

    public void setMessageTemplate(MessageTemplate messageTemplate) {

    }
}
