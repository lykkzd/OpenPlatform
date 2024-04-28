package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月08日 15:59
 */
public class BusinessException extends RuntimeException{

    private ResultCodeEnum resultCodeEnum;
    
    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum  = resultCodeEnum;
    }
    
    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }
}
