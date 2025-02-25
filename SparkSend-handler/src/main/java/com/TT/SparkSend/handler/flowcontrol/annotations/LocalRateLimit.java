package com.TT.SparkSend.handler.flowcontrol.annotations;

import com.TT.SparkSend.handler.enums.RateLimitStrategy;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @Description 单机限流注解
 * @Author TT
 * @Date 2025/2/25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface LocalRateLimit {
    // 设置限流方式，默认为根据请求数限流
    RateLimitStrategy rateLimitStrategy() default RateLimitStrategy.REQUEST_RATE_LIMIT;
}
