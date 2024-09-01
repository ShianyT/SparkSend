package com.TT.SparkSend.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 枚举工具类：获取枚举的描述，获取枚举的code，获取枚举的code列表
 * @Author TT
 * @Date 2024/9/1
 */
public class EnumUtil {
    private EnumUtil() {
    }

    /**
     * 获取枚举的描述
     */
    public static <T extends PowerfulEnum> String getDescriptionByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().map(PowerfulEnum::getDescription).orElse("");
    }

    /**
     * 通过code获取枚举
     */
    public static <T extends PowerfulEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().orElse(null);
    }

    /**
     * 获取枚举的code列表
     */
    public static <T extends PowerfulEnum> List<Integer> getCodeList(Class<T> enumClass) {
        return Arrays.stream((enumClass.getEnumConstants()))
                .map(PowerfulEnum::getCode)
                .collect(Collectors.toList());
    }

}

