package com.TT.SparkSend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/28
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespStatusEnum {
    /**
     * 错误
     */
    ERROR_500("500", "服务器未知错误"),
    ERROR_400("400", "错误请求"),

    /**
     * OK：操作成功
     */
    SUCCESS("0", "操作成功"),
    FAIL("-1", "操作失败"),

    /**
     * pipeline
     */
    CONTEXT_IS_NULL("P00001","流程上下文为空"),
    BUSINESS_CODE_IS_NULL("P0002","业务代码为空"),
    PROCESS_TEMPLATE_IS_NULL("P0003","流程模板配置为空"),
    PROCESS_LIST_IS_NULL("P0004","业务处理器配置为空"),

    ;



    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;
    }
