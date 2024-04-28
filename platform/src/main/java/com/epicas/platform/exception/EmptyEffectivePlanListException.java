package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月08日 16:06
 */
public class EmptyEffectivePlanListException extends BusinessException{
    public EmptyEffectivePlanListException() {
        super(ResultCodeEnum.EMPTY_EFFECTIVE_PLAN_LIST);
    }
}
