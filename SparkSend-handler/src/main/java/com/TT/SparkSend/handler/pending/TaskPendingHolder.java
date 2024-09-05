package com.TT.SparkSend.handler.pending;

import com.TT.SparkSend.handler.config.HandlerThreadPoolConfig;
import com.TT.SparkSend.handler.utils.GroupIdMappingUtils;
import com.TT.SparkSend.support.utils.ThreadPoolUtils;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description 存储每种消息类型与 TaskPending的关系
 * @Author TT
 * @Date 2024/9/2
 */
@Component
public class TaskPendingHolder {
    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    @Autowired
    private ThreadPoolUtils threadPoolUtils;
    private Map<String, ExecutorService> holder = new HashMap<>(32);

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init() {
        /**
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml 动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolUtils.register(executor);
            holder.put(groupId, executor);
        }
    }

    /**
     * 得到对应的线程池
     */
    public ExecutorService route(String groupId) {
        return holder.get(groupId);
    }
}
