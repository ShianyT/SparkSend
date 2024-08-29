package com.TT.SparkSend.service.api.impl.action;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.TT.SparkSend.common.constant.CommonConstant;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.RespStatusEnum;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.service.api.domain.MessageParam;
import com.TT.SparkSend.service.api.enums.BusinessCode;
import com.TT.SparkSend.service.api.impl.domain.SendTaskModel;
import com.TT.SparkSend.support.dao.MessageTemplateDao;
import com.TT.SparkSend.support.domain.MessageTemplate;
import com.TT.SparkSend.support.pipeline.BusinessProcess;
import com.TT.SparkSend.support.pipeline.ProcessContext;
import com.TT.SparkSend.support.utils.TaskInfoUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.ContentModel;
import java.util.*;

/**
 * @Description 拼装参数
 * @Author TT
 * @Date 2024/7/29
 */
@Slf4j
@Service
public class SendAssembleAction implements BusinessProcess<SendTaskModel> {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();

        try {
            // 消息模板对象不存在 / 已被删除
            Optional<MessageTemplate> messageTemplate = messageTemplateDao.findById(messageTemplateId);
            if (!messageTemplate.isPresent() || messageTemplate.get().getIsDeleted().equals(CommonConstant.TRUE)) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
                return;
            }
            // 组装参数
            List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel, messageTemplate.get());
            sendTaskModel.setTaskInfo(taskInfos);

        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("拼装参数任务错误！templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 组装 TaskInfo 任务消息
     *
     * @param sendTaskModel
     * @param messageTemplate
     * @return
     */
    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();

        for (MessageParam messageParam : messageParamList) {
            TaskInfo taskInfo = TaskInfo.builder()
                    .messageId(TaskInfoUtils.generateMessageId())
                    .bizId(messageParam.getBizId())
                    .messageTemplateId(messageTemplate.getId())
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getTemplateType(), messageTemplate.getId()))
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrPool.C_COMMA)))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .shieldType(messageTemplate.getShieldType())
                    .sendAccount(messageTemplate.getSendAccount())
                    .contentModel(getContentModelValue(messageTemplate, messageParam)).build();

            if (CharSequenceUtil.isBlank(taskInfo.getBizId())) {
                taskInfo.setBizId(taskInfo.getMessageId());
            }

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;
    }

    /**
     * 获取 contentModel，替换模板msgContent中占位符信息
     */
    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {
        return null;


    }


}
