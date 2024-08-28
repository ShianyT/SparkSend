package com.TT.SparkSend.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.RespStatusEnum;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.service.api.impl.domain.SendTaskModel;
import com.TT.SparkSend.support.pipeline.BusinessProcess;
import com.TT.SparkSend.support.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 后置参数检查
 * @Author TT
 * @Date 2024/7/29
 */
@Service
public class SendAfterCheckAction implements BusinessProcess<SendTaskModel> {


    public static final String PHONE_REGEX_EXP = "";
    public static final String EMAIL_REGEX_EXP = "";

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();

        // 过滤掉不合法的手机号、邮件
        filterIllegalReceiver(taskInfo);

        if(CollUtil.isEmpty(taskInfo)) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLINET_BAD_PARAMETERS));
        }

    }

    /**
     * 如果指定类型是手机号，检测输入手机号是否合法
     * 如果指定类型是邮件，检测输入邮件是否合法
     * @param taskInfo
     */
    private void filterIllegalReceiver(List<TaskInfo> taskInfo) {
    }
}
