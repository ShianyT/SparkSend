package com.TT.SparkSend.support.utils;

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author TT
 * @Date 2024/8/29
 */
public class ContentHolderUtil {
    /**
     * 占位符前缀
     */
    private static final String PLACE_HOLDER_PREFIX = "{$";
    /**
     * 占位符后缀
     */
    private static final String PLACE_HOLDER_SUFFIX = "}";
    private static final PropertyPlaceholderHelper PROPERTY_PLACEHOLDER_HELPER = new PropertyPlaceholderHelper(PLACE_HOLDER_PREFIX, PLACE_HOLDER_SUFFIX);

    private ContentHolderUtil() {
    }

    public static String replacePlaceHolder(final String template, final Map<String, String> paramMap) {
        return PROPERTY_PLACEHOLDER_HELPER.replacePlaceholders(template,new CustomPlaceHolderResolver(template,paramMap));
    }

    // 自定义数据源来替换占位符
    private static class CustomPlaceHolderResolver implements PropertyPlaceholderHelper.PlaceholderResolver{
        private final String template;
        private final Map<String,String> paramMap;

        public CustomPlaceHolderResolver(String template, Map<String,String> paramMap) {
            super();
            this.template = template;
            this.paramMap = paramMap;
        }

        @Override
        public String resolvePlaceholder(String placeholderName) {
            if (Objects.isNull(paramMap)) {
                String errorStr = MessageFormat.format("template:{0} require param:{1},but not exist! paramMap:{2}", template, placeholderName, paramMap);
                throw new IllegalArgumentException(errorStr);
            }
            String value = paramMap.get(placeholderName);
            if (StringUtils.isEmpty(value)) {
                String errorStr = MessageFormat.format("template:{0} require param:{1},but not exist! paramMap:{2}", template, placeholderName, paramMap);
                throw new IllegalArgumentException(errorStr);
            }
            return value;
        }
    }


}
