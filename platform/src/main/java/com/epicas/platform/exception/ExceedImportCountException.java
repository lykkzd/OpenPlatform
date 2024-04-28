package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月19日 13:42
 */
public class ExceedImportCountException extends BusinessException{

    public ExceedImportCountException() {
        super(ResultCodeEnum.EXCEED_IMPORT_SCOPE);
    }
}
