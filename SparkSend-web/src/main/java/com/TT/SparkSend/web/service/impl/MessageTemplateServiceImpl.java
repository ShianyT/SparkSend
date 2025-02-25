package com.TT.SparkSend.web.service.impl;

import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.support.dao.MessageTemplateDao;
import com.TT.SparkSend.support.domain.MessageTemplate;
import com.TT.SparkSend.web.service.MessageTemplateService;
import cron.xxl.entity.XxlJobInfo;
import cron.xxl.service.CronTaskService;
import cron.xxl.utils.XxlJobUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @Description 消息模板管理 实现类
 * @Author TT
 * @Date 2024/9/18
 */
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Autowired
    private CronTaskService cronTaskService;

    @Autowired
    private XxlJobUtils xxlJobUtils;

    @Override
    public BasicResultVO startCronTask(Long id) {
        // 1、获取消息模板的信息
        MessageTemplate messageTemplate = messageTemplateDao.findById(id).orElse(null);
        if (Objects.isNull(messageTemplate)) {
            return null;
        }

        // 2、动态创建或更新定时任务
        XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);
        return null;
    }
}
