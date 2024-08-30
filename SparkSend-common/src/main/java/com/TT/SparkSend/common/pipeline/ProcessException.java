package com.TT.SparkSend.common.pipeline;

import com.TT.SparkSend.common.enums.RespStatusEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Description 自定义流程处理异常
 * @Author TT
 * @Date 2024/7/28
 */

@Getter
public class ProcessException extends Exception{

    private ProcessContext processContext;

    public ProcessException(ProcessContext processContext){
        super();
        this.processContext = processContext;
    }

    public ProcessException(ProcessContext processContext, Throwable cause) {
        super(cause);
        this.processContext = processContext;
    }

    @Override
    public String getMessage() {
        if(Objects.nonNull(this.processContext)){
            return this.processContext.getResponse().getMsg();
        }
        return RespStatusEnum.CONTEXT_IS_NULL.getMsg();
    }

    public ProcessContext getProcessContext() {
        return processContext;
    }
}
