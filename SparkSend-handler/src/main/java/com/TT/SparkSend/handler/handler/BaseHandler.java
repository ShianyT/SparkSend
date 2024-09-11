package com.TT.SparkSend.handler.handler;

import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.TT.SparkSend.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;

/**
 * @Description 发送各个渠道的handler
 * @Author TT
 * @Date 2024/9/11
 */
public abstract class BaseHandler implements Handler{
    /**
     * 标识渠道的Code
     * 子类初始化的时候指定
     */
    protected Integer channelCode;

    /**
     * 限流相关的参数
     */

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 初始化渠道与Handler的映射关系
     */
    @PostConstruct
    public void init() {
        handlerHolder.putHandler(channelCode,this);
    }

    @Override
    public void doHandler(TaskInfo taskInfo) {
        // TODO
        if (handler(taskInfo)) {
            logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_SUCCESS.getCode()).bizId(taskInfo.getBizId())
                    .messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver())
                    .build());
            return;
        }
        logUtils.print(AnchorInfo.builder().state(AnchorState.SEND_FAIL.getCode()).bizId(taskInfo.getBizId())
                .messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver())
                .build());

    }

    /**
     * 统一处理的handler接口
     */
    public abstract boolean handler(TaskInfo taskInfo);
}
