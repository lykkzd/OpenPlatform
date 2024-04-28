package com.epicas.platform.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回结果集
 * @author liuyang
 * @date 2023年10月08日 14:44
 */
@Data
@ApiModel(description = "通用响应结果")
public class Result<T> implements Serializable{

    @ApiModelProperty("状态：true成功 false失败")
    private Boolean status;

    @ApiModelProperty("业务状态码：20000成功 其他失败")
    private Integer code;

    @ApiModelProperty(value = "响应消息",example = "成功")
    private String message;

    @ApiModelProperty("响应数据")
    private T data;

    public static <T>Result<T> ok(){
        return ok(null);
    }

    public static <T>Result<T> ok(T data){
        Result<T> result = build(ResultCodeEnum.SUCCESS);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T>Result<T> error(){
        return build(ResultCodeEnum.COMMON_FAIL);
    }

    public static <T>Result<T> build(ResultCodeEnum resultCodeEnum){
        Result<T> result = new Result<>();
        result.setStatus(resultCodeEnum.getStatus());
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T>Result<T> validateFailed(String errorInfo) {
        Result<T> result = new Result<>();
        result.setStatus(false);
        result.setCode(50000);
        result.setMessage(errorInfo);
        return result;
    }
}
