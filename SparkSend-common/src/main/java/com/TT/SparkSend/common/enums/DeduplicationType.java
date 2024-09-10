package com.TT.SparkSend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description 去重类型枚举
 * @Author TT
 * @Date 2024/9/9
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationType implements PowerfulEnum{

    /**
     * 相同内容去重
     */
    CONTENT(10, "相同内容去重"),
    /**
     * 渠道接受消息频次去重
     */
    FREQUENCY(20, "一天内N次相同渠道去重");


    private Integer code;
    private String description;

}
