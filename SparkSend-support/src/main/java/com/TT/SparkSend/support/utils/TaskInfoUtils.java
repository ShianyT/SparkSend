package com.TT.SparkSend.support.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.IdUtil;
import com.TT.SparkSend.common.constant.CommonConstant;

import java.util.Date;

/**
 * @Description 生成消息推送的URL 工具类
 * @Author TT
 * @Date 2024/8/28
 */
public class TaskInfoUtils {

    private static final int TPYE_FLAG = 1000000;
    private static final String CODE = "track_code_bid";

    private TaskInfoUtils() {
    }

    /**
     * 生成任务唯一id
     */
    public static String generateMessageId() {
        return IdUtil.nanoId();
    }

    /**
     * 生成BusinessId
     * 模板类型+模板ID+当天日期
     * (固定16位)
     */
    public static Long generateBusinessId(Integer templateType, Long templateId) {
        Integer today = Integer.valueOf(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN));
        return Long.valueOf(String.format("%d%s", templateType * TPYE_FLAG + templateId, today));
    }

    /**
     * 第二到8位为MessageTemplateId 切割出模板ID
     */
    public static Long getMessageTemplateIdFromBusinessId(Long businessId) {
        return Long.valueOf(String.valueOf(businessId).substring(1, 8));
    }

    /**
     * 从businessId切割出日期
     */
    public static Long getDateFormBusinessID(Long businessId) {
        return Long.valueOf(String.valueOf(businessId).substring(8));
    }

    /**
     * 对url添加平台参数（用于追踪数据)
     */
    public static String generateUrl(String url, Long templateId, Integer templateType) {
        url = url.trim();
        Long businessId = generateBusinessId(templateType, templateId);
        if (url.indexOf(CommonConstant.QM) == -1) { // ?track_code_bid=bid(没有参数，添加参数符号)
            return url + CommonConstant.QM_STRING + CODE + CommonConstant.EQUAL_STRING + businessId;
        } else { //&track_code_bid=bid(有参数，在参数后面继续添加参数)
            return url + CommonConstant.AND_STRING + CODE + CommonConstant.EQUAL_STRING + businessId;
        }
    }

}
