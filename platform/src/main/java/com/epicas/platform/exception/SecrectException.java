package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月16日 10:09
 */
public class SecrectException extends BusinessException{

    public SecrectException() {
        super(ResultCodeEnum.SECRECT_ERROR);
    }

}
