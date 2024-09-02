package com.TT.SparkSend.support.utils;

import com.TT.SparkSend.support.config.ThreadPoolExecutorShutdownDefinition;
import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 线程池工具类
 * @Author TT
 * @Date 2024/9/2
 */
@Component
public class ThreadPoolUtils {
    private static final String SOURCE_NAME = "SparkSend";
    @Autowired
    private ThreadPoolExecutorShutdownDefinition threadPoolExecutorShutdownDefinition;

    /**
     * 1、将当前线程池加入到动态线程池内
     * 2、注册线程池被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);
        threadPoolExecutorShutdownDefinition.registryExecutor(dtpExecutor);
    }
}
