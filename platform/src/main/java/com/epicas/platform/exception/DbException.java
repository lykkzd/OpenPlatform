package com.epicas.platform.exception;

import com.epicas.platform.result.ResultCodeEnum;

/**
 * 数据库异常
 * @author liuyang
 * @date 2023年10月10日 16:46
 */
public class DbException extends BusinessException {
    public DbException() {
        super(ResultCodeEnum.MYSQL_OPERATION_EXCEPTION);
    }
}
