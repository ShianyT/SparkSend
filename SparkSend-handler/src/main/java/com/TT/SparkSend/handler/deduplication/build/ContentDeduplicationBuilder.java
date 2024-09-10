package com.TT.SparkSend.handler.deduplication.build;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.AnchorState;
import com.TT.SparkSend.common.enums.DeduplicationType;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;

import java.util.Objects;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder{

    public ContentDeduplicationBuilder() {
        deduplicateType = DeduplicationType.CONTENT.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamFromConfig(deduplicateType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION);
        return deduplicationParam;
    }
}
