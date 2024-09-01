package com.TT.SparkSend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/1
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageType implements PowerfulEnum{
    /**
     * 通知类消息
     */
    NOTICE(10, "通知类消息", "notice"),
    /**
     * 营销类消息
     */
    MARKETING(20,"营销类消息", "marketing"),
    /**
     * 验证码消息
     */
    AUTH_CODE(30,"验证码消息","auth_code");

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    /**
     * 英文标识
     */
    private String codeEn;
}
