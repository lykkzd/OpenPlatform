package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * @author liuyang
 * @date 2023年10月16日 17:34
 */
public class ExcelParseException extends BusinessException{

    public ExcelParseException() {
        super(ResultCodeEnum.PARSE_EXCEL_ERROR);
    }

}
