package com.TT.SprarkSend.stream;

import com.TT.SparkSend.common.domain.AnchorInfo;
import com.TT.SprarkSend.stream.constants.SparkSendFlinkConstant;
import com.TT.SprarkSend.stream.function.SparkSendFlatMapFunction;
import com.TT.SprarkSend.stream.sink.SparkSendSink;
import com.TT.SprarkSend.stream.utils.MessageQueueUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description
 * @Author TT
 * @Date 2025/2/25
 */
public class SparkSendBootStrap {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /**
         * 获取kafkaSource
         */
        KafkaSource<String> kafkaConsumer = MessageQueueUtils.getKafkaConsumer(SparkSendFlinkConstant.TOPIC_NAME, SparkSendFlinkConstant.GROUP_ID, SparkSendFlinkConstant.BROKER);
        DataStreamSource<String> kafkaSource = env.fromSource(kafkaConsumer, WatermarkStrategy.noWatermarks(), SparkSendFlinkConstant.SOURCE_NAME);

        /**
         * 数据转换处理
         */
        SingleOutputStreamOperator<AnchorInfo> dataStream = kafkaSource.flatMap(new SparkSendFlatMapFunction()).name(SparkSendFlinkConstant.FUNCTION_NAME);

        /**
         * 将实时数据多维度写入Redis，离线数据写入hive
         */
        dataStream.addSink(new SparkSendSink()).name(SparkSendFlinkConstant.SINK_NAME);
        env.execute(SparkSendFlinkConstant.JOB_NAME);



    }

}
