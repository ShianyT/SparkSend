package com.TT.SparkSend.service.api.impl.service;

import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.service.api.domain.BatchSendRequest;
import com.TT.SparkSend.service.api.domain.SendRequest;
import com.TT.SparkSend.service.api.domain.SendResponse;
import com.TT.SparkSend.service.api.impl.domain.SendTaskModel;
import com.TT.SparkSend.service.api.service.SendService;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.common.pipeline.ProcessController;
import com.TT.SparkSend.common.pipeline.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext<ProcessModel> context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success())
                .build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(),process.getResponse().getMsg());
    }

    @Override
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        return null;
    }
}
