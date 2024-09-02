package com.TT.SparkSend.support.service;

/**
 * @Description 读取配置服务
 * @Author TT
 * @Date 2024/9/2
 */
public interface ConfigService {
    /**
     * 读取配置
     * 1、当启动使用了apollo或者nacos，优先读取远程配置
     * 2、当没有启动远程配置，读取本地local.properties 配置文件的内容
     */
    String getProperty(String key, String defaultValue);
}
