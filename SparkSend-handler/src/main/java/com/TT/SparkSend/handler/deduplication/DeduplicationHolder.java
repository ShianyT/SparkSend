package com.TT.SparkSend.handler.deduplication;

import com.TT.SparkSend.handler.deduplication.build.Builder;
import com.TT.SparkSend.handler.deduplication.service.DeduplicationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author TT
 * @Date 2024/9/9
 */
@Service
public class DeduplicationHolder {
    private final Map<Integer, Builder> builderHolder = new HashMap<>();
    private final Map<Integer, DeduplicationService> serviceHolder = new HashMap<>();

    public Builder selectBuilder(Integer key) {
        return builderHolder.get(key);
    }

    public DeduplicationService selectService(Integer key) {
        return serviceHolder.get(key);
    }

    public void putBuilder(Integer key, Builder builder) {
        builderHolder.put(key, builder);
    }

    public void putService(Integer key, DeduplicationService service) {
        serviceHolder.put(key, service);
    }
}
