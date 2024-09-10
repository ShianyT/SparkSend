package com.TT.SparkSend.handler.deduplication.build;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public interface Builder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication";

    /**
     * 根据配置构建去重从参数
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);
}
