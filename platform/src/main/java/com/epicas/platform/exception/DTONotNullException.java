package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月27日 10:54
 */
public class DTONotNullException extends BusinessException{

    public DTONotNullException() {
        super(ResultCodeEnum.DTO_NOT_NULL);
    }
}
