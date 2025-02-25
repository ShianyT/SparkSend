package com.TT.SprarkSend.stream.callback;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.List;

/**
 * @Description redis pipeline接口定义
 * @Author TT
 * @Date 2025/2/25
 */
public interface RedisPipelineCallBack {
    List<RedisFuture<?>> invoke(RedisAsyncCommands redisAsyncCommands);
}
