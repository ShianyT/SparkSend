package com.TT.SprarkSend.stream.utils;

import com.TT.SprarkSend.stream.callback.RedisPipelineCallBack;
import com.TT.SprarkSend.stream.constants.SparkSendFlinkConstant;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import org.springframework.data.redis.connection.RedisPipelineException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 无Spring环境下使用Redis，基于Lettuce封装
 * @Author TT
 * @Date 2025/2/25
 */
public class LettuceRedisUtils {
    /**
     * 初始化 redisClient
     */
    private static RedisClient redisClient;

    static {
        RedisURI redisUri = RedisURI.Builder.redis(SparkSendFlinkConstant.REDIS_IP)
                .withPort(Integer.valueOf(SparkSendFlinkConstant.REDIS_PORT))
                .withPassword(SparkSendFlinkConstant.REDIS_PASSWORD.toCharArray())
                .build();
        redisClient = RedisClient.create(redisUri);
    }

    private LettuceRedisUtils() {

    }

    public static void pipeline(RedisPipelineCallBack  pipelineCallBack) {
        // 获取redis链接
        StatefulRedisConnection<byte[], byte[]> connect = redisClient.connect(new ByteArrayCodec());
        // 获取异步命令接口
        RedisAsyncCommands<byte[], byte[]> commands = connect.async();

        // 执行回调，获得RedisFuture列表
        List<RedisFuture<?>> futures = pipelineCallBack.invoke(commands);

        // 刷新管道，将命令发送到redis服务器
        commands.flushCommands();
        // 等待所有操作完成，超时时间为10s
        LettuceFutures.awaitAll(10, TimeUnit.SECONDS,
                futures.toArray(new RedisFuture[futures.size()]));
        connect.close();
    }
}
