package com.TT.SparkSend.handler.flowcontrol;

import com.TT.SparkSend.common.constant.CommonConstant;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.common.enums.ChannelType;
import com.TT.SparkSend.common.enums.EnumUtil;
import com.TT.SparkSend.handler.enums.RateLimitStrategy;
import com.TT.SparkSend.handler.flowcontrol.annotations.LocalRateLimit;
import com.TT.SparkSend.support.service.ConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author TT
 * @Date 2025/2/25
 */
@Slf4j
public class FlowControlFactory implements ApplicationContextAware {

    private static final String FLOW_CONTROL_KEY = "flowControlRule";
    private static final String FLOW_CONTROL_PREFIX = "flow_control_";

    private final Map<RateLimitStrategy, FlowControlService> flowControlServiceMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;
    @Autowired
    private ConfigService config;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
        RateLimiter rateLimiter;
        Double rateInitValue = flowControlParam.getRateInitValue();

        // 对比 初始限流值 和 配置限流值,以配置限流值为准（可随时调整）
        Double rateLimitConfig = getRateLimitConfig(taskInfo.getSendChannel());
        if (Objects.nonNull(rateLimitConfig) && !rateInitValue.equals(rateLimitConfig)) {
            rateLimiter = RateLimiter.create(rateLimitConfig);
            flowControlParam.setRateInitValue(rateLimitConfig);
            flowControlParam.setRateLimiter(rateLimiter);
        }

        FlowControlService flowControlService = flowControlServiceMap.get(flowControlParam.getRateLimitStrategy());
        if (Objects.isNull(flowControlService)) {
            log.error("没有找到对应的单机限流策略");
            return;
        }
        Double costTime = flowControlService.FlowControl(taskInfo, flowControlParam);
        if (costTime > 0) {
            log.info("consumer {} flow control time {}",
                    EnumUtil.getEnumByCode(taskInfo.getSendChannel(), ChannelType.class).getDescription(), costTime);
        }
    }

    /**
     * 得到限流值的配置
     * apollo配置样例     key：flowControl value：{"flow_control_40":1}
     * @param channelCode
     */
    private Double getRateLimitConfig(Integer channelCode) {
        String flowControlConfig = config.getProperty(FLOW_CONTROL_KEY, CommonConstant.EMPTY_JSON_OBJECT);
        JSONObject jsonObject = JSON.parseObject(flowControlConfig);
        if (Objects.isNull(jsonObject.getDouble(FLOW_CONTROL_PREFIX + channelCode))) {
            return null;
        }
        return jsonObject.getDouble(FLOW_CONTROL_PREFIX + channelCode);
    }

    // 将限流实现子类放入限流集合中
    @PostConstruct
    private void init() {
        Map<String, Object> serviceMap = this.applicationContext.getBeansWithAnnotation(LocalRateLimit.class);
        serviceMap.forEach((name, service) -> {
            if (service instanceof FlowControlService) {
                LocalRateLimit localRateLimit = AopUtils.getTargetClass(service).getAnnotation(LocalRateLimit.class);
                RateLimitStrategy rateLimitStrategy = localRateLimit.rateLimitStrategy();
                //通常情况下 实现的限流service与rateLimitStrategy一一对应
                flowControlServiceMap.put(rateLimitStrategy, (FlowControlService) service);
            }
        });
    }
}
