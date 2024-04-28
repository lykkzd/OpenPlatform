package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月27日 14:09
 */
public class DealOrNotTypeException extends BusinessException{
    public DealOrNotTypeException() {
        super(ResultCodeEnum.DEAL_OR_NOT_TYPE_ERROR);
    }
}
