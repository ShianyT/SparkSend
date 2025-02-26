package com.TT.SparkSend.handler.script;

import com.TT.SparkSend.handler.domain.sms.SmsParam;
import com.TT.SparkSend.support.domain.SmsRecord;

import java.util.List;

/**
 * @Description 短信脚本 接口
 * @Author TT
 * @Date 2025/2/26
 */
public interface SmsScript {
    /**
     * 发送短信
     *
     * @param smsParam
     * @return 渠道商发送接口返回值
     */
    List<SmsRecord> send(SmsParam smsParam);


    /**
     * 拉取回执
     *
     * @param id 渠道账号的ID
     * @return 渠道商回执接口返回值
     */
    List<SmsRecord> pull(Integer id);
}
