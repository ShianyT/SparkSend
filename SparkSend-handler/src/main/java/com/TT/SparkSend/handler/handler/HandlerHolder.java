package com.TT.SparkSend.handler.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description channel -> Handler的映射关系
 * @Author TT
 * @Date 2024/9/11
 */
public class HandlerHolder {
    private Map<Integer, Handler> handlers = new HashMap<>(128);

    public void putHandler(Integer channelCode, Handler handler) {
        handlers.put(channelCode, handler);
    }

    public Handler route(Integer channelCode) {
        return handlers.get(channelCode);
    }
}
