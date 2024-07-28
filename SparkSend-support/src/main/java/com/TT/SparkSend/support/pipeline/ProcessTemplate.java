package com.TT.SparkSend.support.pipeline;

import lombok.Data;

import java.util.List;

/**
 * @Description 业务执行模板（把责任链的逻辑串联起来）
 * @Author TT
 * @Date 2024/7/28
 */
@Data
public class ProcessTemplate {

    private List<BusinessProcess> processList;

}
