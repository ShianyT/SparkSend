package com.TT.SparkSend.web.service;

import com.TT.SparkSend.common.vo.BasicResultVO;

/**
 * @Description 消息模板管理 接口
 * @Author TT
 * @Date 2024/9/18
 */
public interface MessageTemplateService {

    /**
     * 启动模板定时任务
     * @param id
     * @return
     */
    BasicResultVO startCronTask(Long id);


}
