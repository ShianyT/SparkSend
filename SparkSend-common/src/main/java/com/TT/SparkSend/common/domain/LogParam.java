package com.TT.SparkSend.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 日志参数
 * @Author TT
 * @Date 2024/9/2
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogParam {

    /**
     * 需要记录的日志
     */
    private Object object;

    /**
     * 标识日志的业务
     */
    private String bizType;

    /**
     * 生成时间
     */
    private long timestamp;
}
