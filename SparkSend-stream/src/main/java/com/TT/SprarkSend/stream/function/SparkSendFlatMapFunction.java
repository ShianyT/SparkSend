package com.TT.SprarkSend.stream.function;

import com.TT.SparkSend.common.domain.AnchorInfo;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @Description 将输入的json字符串反序列化为AnchorInfo对象，并向下游传递
 * @Author TT
 * @Date 2025/2/25
 */
public class SparkSendFlatMapFunction implements FlatMapFunction<String, AnchorInfo> {

    @Override
    public void flatMap(String value, Collector<AnchorInfo> collector) throws Exception {
        AnchorInfo anchorInfo = JSON.parseObject(value, AnchorInfo.class);
        collector.collect(anchorInfo);
    }
}
