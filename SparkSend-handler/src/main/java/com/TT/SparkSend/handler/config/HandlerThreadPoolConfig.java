package com.TT.SparkSend.handler.config;

import com.TT.SparkSend.common.constant.ThreadPoolConstant;
import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.ThreadPoolBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Description 线程池配置
 * @Author TT
 * @Date 2024/9/2
 */
public class HandlerThreadPoolConfig {

    private static final String PRE_FIX = "SparkSend";

    private HandlerThreadPoolConfig() {

    }

    /**
     * 业务：处理某个渠道的某种类型消息的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收）
     * 动态线程池且被Spring管理：true
     */
    public static DtpExecutor getExecutor(String groupId) {
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(PRE_FIX + groupId)
                // 线程池核心线程数量
                .corePoolSize(ThreadPoolConstant.COMMON_CORE_POOL_SIZE)
                // 线程池的最大线程数
                .maximumPoolSize(ThreadPoolConstant.COMMON_MAX_POOL_SIZE)
                // 当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                .keepAliveTime(ThreadPoolConstant.COMMON_KEEP_LIVE_TIME)
                // 时间单位
                .timeUnit(TimeUnit.SECONDS)
                // 拒绝策略，当提交的任务过多而不能及时处理时，定制策略来处理任务
                /*
                 * AbortPolicy: 抛出RejectedExecutionException来拒绝新任务的处理。
                 * CallerRunsPolicy：调用执行自己的线程运行任务，也就是直接在调用execute方法的线程中运行(run)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。
                 * DiscardPolicy：不处理新任务，直接丢弃掉。
                 * DiscardOldestPolicy：此策略将丢弃最早的未处理的任务请求。
                 */
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                // 是否允许核心线程自己到达某个时间自动销毁
                .allowCoreThreadTimeOut(false)
                // 任务队列，被添加到线程池中，但尚未被执行的任务；它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种；
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), ThreadPoolConstant.COMMON_QUEUE_SIZE, false)
                .buildDynamic();
    }
}
