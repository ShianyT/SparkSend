package com.TT.SparkSend.service.api.impl.config;

import com.TT.SparkSend.service.api.enums.BusinessCode;
import com.TT.SparkSend.service.api.impl.action.SendAfterCheckAction;
import com.TT.SparkSend.service.api.impl.action.SendAssembleAction;
import com.TT.SparkSend.service.api.impl.action.SendPreCheckAction;
import com.TT.SparkSend.common.pipeline.ProcessController;
import com.TT.SparkSend.common.pipeline.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PipelineConfig {

    @Autowired
    private SendPreCheckAction sendPreCheckAction;
    @Autowired
    private SendAssembleAction sendAssembleAction;
    @Autowired
    private SendAfterCheckAction sendAfterCheckAction;

    /**
     * 普通发送执行流程
     * 1、前置参数校验
     * 2、组装参数
     * 3、后置参数校验
     * 4、发送消息至MQ
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(sendPreCheckAction, sendAssembleAction, sendAfterCheckAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode 与 ProcessTemplate
     * @return
     */
    @Bean
    public ProcessController processController(){
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }
}
