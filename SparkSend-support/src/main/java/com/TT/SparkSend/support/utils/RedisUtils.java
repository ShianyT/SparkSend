package com.TT.SparkSend.support.utils;

import cn.hutool.core.collection.CollUtil;
import com.TT.SparkSend.common.constant.CommonConstant;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description 对Redis的某些操作进行二次封装
 * @Author TT
 * @Date 2024/9/7
 */
@Slf4j
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将结果封装为map
     */
    public Map<String, String> mGet(List<String> keys) {
        HashMap<String, String> result = new HashMap<>();
        try {
            List<String> value = redisTemplate.opsForValue().multiGet(keys);
            if (CollUtil.isNotEmpty(value)) {
                for (int i = 0; i < keys.size(); i++) {
                    if (Objects.nonNull(value.get(i))) {
                        result.put(keys.get(i), value.get(i));
                    }
                }
            }
        } catch (Exception e) {
            log.error("RedisUtils#mGet fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    public Map<Object, Object> hGetAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("RedisUtils#hGetAll fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return new HashMap<>(2);
    }

    public List<String> lRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("RedisUtils#lRange fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return new ArrayList<>();
    }

    /**
     * pipeline 设置 key-value 并设置过期时间
     */
    public void pipelineSetEx(Map<String, String> keyValues, Long seconds) {
        try {
            /**
             * pipeline管道机制，它能将一组Redis命令进行组装，通过一次RTT传输给Redis，并将这组Redis命令的执行结果按顺序返回给客户端。
             * 这里用匿名写法
             * redisTemplate.executePipelined(new RedisCallback<String>(){
             *     @Override
             *     public String doInRedis(RedisConnection connection) throws DataAccessException {
             *         ...
             *  // 通过阅读RedisTemplate源码可以看到：
             *  // Callback cannot return a non-null value as it gets overwritten by the pipeline
             *  // (回调无法返回非null值，因为它会被管道覆盖)
             *         return null;
             *     }
             * });
             */
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    // SETEX()命令：有三个参数 set(key, time, value)，在设置的时候给key设置一个过期时间time，时间到了key自动失效。
                    connection.setEx(entry.getKey().getBytes(), seconds,
                            entry.getValue().getBytes());
                }
                return null;
            });
        } catch (Exception e) {
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * lpush方法，并指定过期时间
     */
    public void lPush(String key, String value, Long seconds) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                connection.lPush(key.getBytes(), value.getBytes());
                connection.expire(key.getBytes(), seconds);
                return null;
            });
        } catch (Exception e) {
            log.error("RedisUtils#lPush fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    public Long lLen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("RedisUtils#lLen fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return 0L;
    }

    public String lPop(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("RedisUtils#lPop fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return "";
    }

    /**
     * pipelined 设置key-value 并设置过期时间
     * @param seconds 过期时间
     * @param delta 自增的步长
     */
    public void pipelineHashIncrByEx(Map<String, String> keyValues, Long seconds, Long delta) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    connection.hIncrBy(entry.getKey().getBytes(), entry.getValue().getBytes(), delta);
                    connection.expire(entry.getKey().getBytes(), seconds);
                }
                return null;
            });
        } catch (Exception e) {
            log.error("redis pipelineSetEX fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 执行指定的lua脚本返回执行结果
     * --KEYS[1]: 限流key
     * --ARGV[1]: 限流窗口
     * --ARGV[2]: 当前时间戳（作为score）
     * --ARGV[3]: 阈值
     * --ARGV[4]: score 对应的唯一value
     *
     * @param redisScript lua脚本
     * @param keys 在脚本中所用到的那些 Redis 键(key)
     * @param args 附加参数，在 Lua 中通过全局变量 ARGV 数组访问
     */
    /*
     * Lua 是一种轻量小巧的脚本语言，用标准C语言编写并以源代码形式开放， 其设计目的是为了嵌入应用程序中，从而为应用程序提供灵活的扩展和定制功能。
     */
    public Boolean execLimitLua(RedisScript<Long> redisScript, List<String> keys, String... args) {
        try {
            // 执行lua脚本
            Long execute = redisTemplate.execute(redisScript, keys, args);
            if (Objects.isNull(execute)) {
                return false;
            }
            return CommonConstant.TRUE.equals(execute.intValue());
        } catch (Exception e) {
            log.error("redis execLimitLua fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return false;
    }
}
