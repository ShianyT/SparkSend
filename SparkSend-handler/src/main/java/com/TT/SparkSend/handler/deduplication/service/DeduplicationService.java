package com.TT.SparkSend.handler.deduplication.service;

import com.TT.SparkSend.handler.deduplication.DeduplicationParam;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public interface DeduplicationService {

    /**
     * 去重
     */
    void deduplication(DeduplicationParam param);

}
