package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月27日 14:09
 */
public class ShowTypeException extends BusinessException{
    public ShowTypeException() {
        super(ResultCodeEnum.SHOW_TYPE_ERROR);
    }
}
