package com.TT.SparkSend.handler.action;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.constant.CommonConstant;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.DeduplicationType;
import com.TT.SparkSend.common.enums.EnumUtil;
import com.TT.SparkSend.common.pipeline.BusinessProcess;
import com.TT.SparkSend.common.pipeline.ProcessContext;
import com.TT.SparkSend.handler.deduplication.DeduplicationHolder;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;
import com.TT.SparkSend.support.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 去重服务
 *           1. 根据相同内容N分钟去重（SlideWindowLimitService）
 *           2. 相同的渠道一天内频次去重（SimpleLimitService）
 * @Author TT
 * @Date 2024/9/9
 */
@Service
public class DeduplicationAction implements BusinessProcess<TaskInfo> {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    @Autowired
    private ConfigService config;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        // 配置样例{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":"5"}}
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, CommonConstant.EMPTY_JSON_OBJECT);

        // 去重
        List<Integer> deduplicationList = EnumUtil.getCodeList(DeduplicationType.class);
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam
                    = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (Objects.nonNull(deduplicationParam)) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }
        if (CollUtil.isEmpty(taskInfo.getReceiver())) {
            context.setNeedBreak(true);
        }
    }
}
