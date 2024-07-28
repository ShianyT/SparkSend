package com.TT.SparkSend.common.vo;

import com.TT.SparkSend.common.enums.RespStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @Author TT
 * @Date 2024/7/28
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BasicResultVO<T> {

    /**
     * 响应状态
     */
    private String status;

    /**
     * 响应编码
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public BasicResultVO(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BasicResultVO(RespStatusEnum status){
        this(status,null);
    }

    public BasicResultVO(RespStatusEnum status, T data) {
        this(status, status.getMsg(), data);
    }

    public BasicResultVO(RespStatusEnum status, String msg, T data) {
        this.status = status.getCode();
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认成功响应
     * @return
     */
    public static BasicResultVO<Void> success(){
        return new BasicResultVO<>(RespStatusEnum.SUCCESS);
    }

    /**
     * 自定义信息的成功响应
     * 通常用作插入成功等并显示具体操作通知如: return BasicResultVO.success("发送信息成功")
     */
    public static <T> BasicResultVO<T> success(String msg){
        return new BasicResultVO<>(RespStatusEnum.SUCCESS,msg,null);
    }

    /**
     * 带数据的成功响应
     */
    public static <T> BasicResultVO<T> success(T data){
        return new BasicResultVO<>(RespStatusEnum.SUCCESS,data);
    }

    /**
     * @return 默认失败响应
     */
    public static <T> BasicResultVO<T> fail(){
        return new BasicResultVO<>(RespStatusEnum.FAIL,RespStatusEnum.FAIL.getMsg(), null);
    }

    /**
     * 自定义信息的失败响应
     */
    public static <T> BasicResultVO<T> fail(String msg){
        return new BasicResultVO<>(RespStatusEnum.FAIL,msg,null);
    }

    /**
     * 自定义状态的失败响应
     */
    public static <T> BasicResultVO<T> fail(RespStatusEnum status){
        return new BasicResultVO<>(status);
    }

    /**
     * 自定义状态和信息的失败响应
     */
    public static <T> BasicResultVO<T> fail(RespStatusEnum status,String msg){
        return new BasicResultVO<>(status,msg,null);
    }

}
