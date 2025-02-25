package cron.xxl.enums;

/**
 * @Description 路由策略
 * @Author TT
 * @Date 2024/9/18
 */
public enum ExecutorRouteStrategyEnum {
    /**
     * first
     */
    FIRST,
    /**
     * last
     */
    LAST,
    /**
     * round
     */
    ROUND,
    /**
     * random
     */
    RANDOM,
    /**
     * consistent hash
     */
    CONSISTENT_HASH,
    /**
     * least frequently used
     */
    LEAST_FREQUENTLY_USED,
    /**
     * least_recently_used
     */
    LEAST_RECENTLY_USED,
    /**
     * failover
     */
    FAILOVER,
    /**
     * busyover
     */
    BUSYOVER,
    /**
     * sharding_broadcast
     */
    SHARDING_BROADCAST;

    ExecutorRouteStrategyEnum() {
    }
}
