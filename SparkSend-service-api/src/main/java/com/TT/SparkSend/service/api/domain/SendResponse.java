package com.TT.SparkSend.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 消息发送接口返回值
 * @Author TT
 * @Date 2024/7/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendResponse {

    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;


}
