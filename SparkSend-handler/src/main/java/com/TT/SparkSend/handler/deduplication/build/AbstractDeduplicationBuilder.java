package com.TT.SparkSend.handler.deduplication.build;

import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.deduplication.DeduplicationHolder;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
public abstract class AbstractDeduplicationBuilder implements Builder {

    protected Integer deduplicateType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @PostConstruct
    public void init() {
        deduplicationHolder.putBuilder(deduplicateType, this);
    }

    public DeduplicationParam getParamFromConfig(Integer key, String deduplicationConfig, TaskInfo taskInfo) {
        JSONObject object = JSON.parseObject(deduplicationConfig);
        if (Objects.isNull(object)) {
            return null;
        }
        DeduplicationParam deduplicationParam = JSON.parseObject(object.getString(DEDUPLICATION_CONFIG_PRE + key), DeduplicationParam.class);
        if (Objects.isNull(deduplicationParam)) {
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }

}
