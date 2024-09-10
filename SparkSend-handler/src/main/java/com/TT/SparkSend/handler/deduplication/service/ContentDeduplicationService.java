package com.TT.SparkSend.handler.deduplication.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.DeduplicationType;
import com.TT.SparkSend.handler.deduplication.limit.LimitService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Description 内容去重服务（默认5分钟相同的文案发给相同的用户去重）
 * @Author TT
 * @Date 2024/9/9
 */
@Service
public class ContentDeduplicationService extends AbstractDeduplicationService{

    @Autowired
    public ContentDeduplicationService(@Qualifier("SlideWindowLimitService") LimitService limitService) {
        this.limitService = limitService;
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    /**
     * 内容去重 构建key
     * key: md5(templateId + receiver + content)
     * 相同内容相同模板短时间内发给同一个人
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() +
                receiver + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
