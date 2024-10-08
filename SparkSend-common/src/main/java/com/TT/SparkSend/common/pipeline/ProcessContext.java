package com.TT.SparkSend.common.pipeline;

import com.TT.SparkSend.common.vo.BasicResultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description 责任链上下文
 * @Author TT
 * @Date 2024/7/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessContext<T extends ProcessModel> {

    /**
     * 标识责任链的编码
     */
    private String code;

    /**
     * 存储责任链上下文数据的模型
     */
    private T processModel;

    /**
     * 责任链的中断标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结束
     */
    BasicResultVO response;
}

