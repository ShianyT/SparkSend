package cron.xxl.enums;

/**
 * @Description 执行阻塞队列
 * @Author TT
 * @Date 2024/9/18
 */
public enum ExecutorBlockStrategyEnum {
    /**
     * 单机串行
     */
    SERIAL_EXECUTION,

    /**
     * 丢弃后续调度
     */
    DISCARD_LATER,

    /**
     * 覆盖之前调度
     */
    COVER_EARLY;

    ExecutorBlockStrategyEnum() {
    }
}
