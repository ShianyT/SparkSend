package com.TT.SparkSend.handler.action;

import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.TT.SparkSend.common.enums.ShieldType;
import com.TT.SparkSend.common.pipeline.BusinessProcess;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.support.utils.LogUtils;
import com.TT.SparkSend.support.utils.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * @Description 屏蔽消息：
 * 1、当接受到该消息是夜间，直接屏蔽（不发送）
 * 2、当接收到该消息是夜间，次日9点发送
 * 配合 分布式任务定时任务框架
 * @Author TT
 * @Date 2024/9/2
 */
@Service
public class ShieldAction implements BusinessProcess<TaskInfo> {

    private static final String NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY = "night_shield_send";
    private static final long SECONDS_OF_A_DAY = 86400L;

    /**
     * 默认早上八点之前是凌晨
     */
    private static final int NIGHT = 8;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private LogUtils logUtils;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        if (ShieldType.NIGHT_NO_SHIELD.getCode().equals(taskInfo.getShieldType())) {
            return;
        }

        if (LocalTime.now().getHour() < NIGHT) {
            if (ShieldType.NIGHT_SHIELD.getCode().equals(taskInfo.getShieldType())) {
                logUtils.print(AnchorInfo.builder().state(AnchorState.NIGHT_SHIELD.getCode()).bizId(taskInfo.getBizId())
                        .messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
            }
            if (ShieldType.NIGHT_SHIELD_BUT_NEXT_DAY_SEND.getCode().equals(taskInfo.getShieldType())) {
                redisUtils.lPush(NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY, JSON.toJSONString(taskInfo,
                        SerializerFeature.WriteClassName), SECONDS_OF_A_DAY);
                logUtils.print(AnchorInfo.builder().state(AnchorState.NIGHT_SHIELD_NEXT_SEND.getCode()).bizId(taskInfo.getBizId())
                        .messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).build());
            }
            context.setNeedBreak(true);
        }
    }
}
