package com.TT.SparkSend.common.dto.account.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author TT
 * @Date 2025/2/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsAccount {

    /**
     * 标识渠道商Id
     */
    protected Integer supplierId;

    /**
     * 标识渠道商名字
     */
    protected String supplierName;

    /**
     * 【重要】类名，定位到具体的处理"下发"/"回执"逻辑
     * 依据ScriptName对应具体的某一个短信账号
     */
    protected String scriptName;
}
