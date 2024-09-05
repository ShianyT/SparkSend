package com.TT.SparkSend.handler.pending;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.common.pipeline.ProcessController;
import com.TT.SparkSend.common.pipeline.ProcessModel;
import com.TT.SparkSend.common.vo.BasicResultVO;
import com.TT.SparkSend.handler.config.TaskPipelineConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description Task执行器
 * @Author TT
 * @Date 2024/9/2
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable {
    private TaskInfo taskInfo;

    @Autowired
    /*
    由于ProcessController有多个实现类，当只有@Autowired注解时，spring将不知道注入哪个实现类，故结合
    @Qualifier注解来指定要注入handlerProcessController实现类，从而避免抛出异常。
     */
    @Qualifier("handlerProcessController")
    private ProcessController processController;

    @Override
    public void run() {
        ProcessContext<ProcessModel> context = ProcessContext.builder().processModel(taskInfo)
                .code(TaskPipelineConfig.PIPELINE_HANDLER_CODE)
                .needBreak(false).response(BasicResultVO.success())
                .build();
        processController.process(context);
    }
}
