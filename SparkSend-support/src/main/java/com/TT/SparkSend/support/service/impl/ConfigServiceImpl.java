package com.TT.SparkSend.support.service.impl;

import cn.hutool.setting.dialect.Props;
import com.TT.SparkSend.support.service.ConfigService;

import java.nio.charset.StandardCharsets;

/**
 * @Description 读取配置实现类
 * @Author TT
 * @Date 2024/9/4
 */
public class ConfigServiceImpl implements ConfigService {

    /**
     * 本地配置
     */
    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);

    /**
     * apollo配置 TODO
     */

    /**
     * nacos配置 TODO
     */


    @Override
    public String getProperty(String key, String defaultValue) {
        // TODO
        return props.getProperty(key, defaultValue);
    }
}
