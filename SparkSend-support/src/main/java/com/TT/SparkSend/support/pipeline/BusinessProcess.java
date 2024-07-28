package com.TT.SparkSend.support.pipeline;

/**
 * @Description 责任链业务执行器
 * @Author TT
 * @Date 2024/7/28
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     */
    void process(ProcessContext<T> context);
}
