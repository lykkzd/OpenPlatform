package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月16日 15:21
 */
public class OrgIdNotExistException extends BusinessException{

    public OrgIdNotExistException() {
        super(ResultCodeEnum.ORG_ID_NOT_EXIST);
    }
}
