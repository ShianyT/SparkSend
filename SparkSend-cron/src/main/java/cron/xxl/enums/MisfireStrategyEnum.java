package cron.xxl.enums;

/**
 * @Description 调度过期策略
 * @Author TT
 * @Date 2024/9/18
 */
public enum MisfireStrategyEnum {
    /**
     * do nothing
     */
    DO_NOTHING,
    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategyEnum() {

    }
}
