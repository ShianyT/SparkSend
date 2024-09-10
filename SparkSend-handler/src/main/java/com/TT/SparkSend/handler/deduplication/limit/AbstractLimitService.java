package com.TT.SparkSend.handler.deduplication.limit;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.deduplication.service.AbstractDeduplicationService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public abstract class AbstractLimitService implements LimitService {

    /**
     * 获取得到当前消息模板所有的去重key
     */
    protected List<String> deduplicationAllKey(AbstractDeduplicationService service, TaskInfo taskInfo) {
        List<String> result = new ArrayList<>(taskInfo.getReceiver().size());
        for (String receiver : taskInfo.getReceiver()) {
            String key = deduplicationSingleKey(service, taskInfo, receiver);
            result.add(key);
        }
        return result;
    }

    protected String deduplicationSingleKey(AbstractDeduplicationService service, TaskInfo taskInfo, String receiver) {
        return service.deduplicationSingleKey(taskInfo, receiver);
    }
}
