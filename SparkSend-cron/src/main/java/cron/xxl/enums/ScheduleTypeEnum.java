package cron.xxl.enums;

/**
 * @Description 调度类型
 * @Author TT
 * @Date 2024/9/18
 */
public enum ScheduleTypeEnum {
    /**
     * none
     */
    NONE,
    /**
     * schedule by cron
     */
    CRON,
    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE,
    ;

    ScheduleTypeEnum() {

    }
}
