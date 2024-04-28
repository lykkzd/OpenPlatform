package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月08日 16:04
 */
public class CarNumberNotExistException extends BusinessException{
    public CarNumberNotExistException() {
        super(ResultCodeEnum.CAR_NUMBER_NOT_EXIST);
    }
}
