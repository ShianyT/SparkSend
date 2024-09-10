package com.TT.SparkSend.handler.deduplication;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 去重服务所需要的参数
 * @Author TT
 * @Date 2024/9/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {

    /**
     * TaskInfo信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间
     * 单位：秒
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需要达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识属于哪种去重(数据埋点)
     */
    private AnchorState anchorState;
}
