package com.TT.SparkSend.service.api.impl.action;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.TT.SparkSend.common.enums.RespStatusEnum;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.service.api.domain.MessageParam;
import com.TT.SparkSend.service.api.impl.domain.SendTaskModel;
import com.TT.SparkSend.support.pipeline.BusinessProcess;
import com.TT.SparkSend.support.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 发送消息 前置参数检查
 * @Author TT
 * @Date 2024/7/28
 */
@Service
public class SendPreCheckAction implements BusinessProcess<SendTaskModel> {


    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();

        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();

        // 1. 没有传入 消息模板Id 或者 messageParam
        if (Objects.isNull(messageTemplateId) || messageParamList.isEmpty()) {
            context.setNeedBreak(true)
                    .setResponse(BasicResultVO.fail(RespStatusEnum.CLINET_BAD_PARAMETERS, "模板ID或参数列表为空"));
            return;
        }

        // 2. 过滤 receiver = null的messageParam
        List<MessageParam> resultMessageParamList = messageParamList.stream()
                .filter(messageParam -> !CharSequenceUtil.isBlank(messageParam.getReceiver())).toList();
        if (resultMessageParamList.isEmpty()) {
            context.setNeedBreak(true)
                    .setResponse(BasicResultVO.fail(RespStatusEnum.CLINET_BAD_PARAMETERS,"含接受者的参数列表为空"));
            return;
        }

        // 3. 过滤 receiver 大于100的请求
        if (resultMessageParamList.stream().anyMatch(messageParam -> messageParam.getReceiver().split(StrPool.COMMA).length > 100)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TOO_MANY_RECEIVER));
            return;
        }

        sendTaskModel.setMessageParamList(resultMessageParamList);

    }
}
