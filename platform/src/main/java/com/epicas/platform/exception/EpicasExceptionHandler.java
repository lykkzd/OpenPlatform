package com.epicas.platform.exception;

import com.epicas.platform.result.Result;
import com.epicas.platform.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


/**
 * @author liuyang
 * @date 2023年10月08日 15:56
 */
@Slf4j
@RestControllerAdvice
public class EpicasExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleAbstractException(BusinessException e){
        ResultCodeEnum resultCodeEnum = e.getResultCodeEnum();
        return Result.build(resultCodeEnum);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("|"));
        log.error("请求参数校验异常 -> {}", msg);
        log.debug("", e);
        return Result.validateFailed(msg);
    }
}
