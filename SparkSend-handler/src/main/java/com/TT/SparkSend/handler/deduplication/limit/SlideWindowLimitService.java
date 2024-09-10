package com.TT.SparkSend.handler.deduplication.limit;

import cn.hutool.core.util.IdUtil;
import com.TT.SparkSend.common.domain.TaskInfo;
import com.TT.SparkSend.handler.deduplication.DeduplicationParam;
import com.TT.SparkSend.handler.deduplication.service.AbstractDeduplicationService;
import com.TT.SparkSend.support.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 滑动窗口去重器（内容去重采用基于redis中zset的滑动窗口去重，可以做到严格控制单位时间内的频次。）
 * 业务逻辑：5分钟内相同用户如果收到相同的内容，则应该被过滤掉
 * 技术方案：由lua脚本实现
 * @Author TT
 * @Date 2024/9/9
 */
@Service(value = "SlideWindowLimitService")
public class SlideWindowLimitService extends AbstractLimitService {

    private static final String LIMIT_TAG = "SW_";

    @Autowired
    private RedisUtils redisUtils;

    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    }

    /**
     * @param service  去重器对象
     * @param taskInfo
     * @param param    去重参数
     * @return 返回不符合条件的手机号码
     */
    @Override
    public Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param) {
        Set<String> filterReceiver = new HashSet<>(taskInfo.getReceiver().size());
        long nowTime = System.currentTimeMillis();
        for (String receiver : taskInfo.getReceiver()) {
            String key = LIMIT_TAG + deduplicationSingleKey(service, taskInfo, receiver);
            // 使用Twitter的Snowflake算法，可以在分布式系统中，生成简单的、按照时间有序生成的全局唯一ID
            String scoreValue = String.valueOf(IdUtil.getSnowflake().nextId());
            String score = String.valueOf(nowTime);

            // 执行lua脚本并返回执行结果
            final Boolean result = redisUtils.execLimitLua(redisScript, Collections.singletonList(key),
                    String.valueOf(param.getDeduplicationTime() * 1000), score,
                    String.valueOf(param.getCountNum()), scoreValue);
            if (Boolean.TRUE.equals(result)) {
                filterReceiver.add(receiver);
            }
        }
        return filterReceiver;
    }
}
