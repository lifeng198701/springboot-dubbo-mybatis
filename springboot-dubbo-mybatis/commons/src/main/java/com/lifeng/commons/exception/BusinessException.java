package com.lifeng.commons.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    private final ErrorCodeEnum errorCodeEnum;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }
    public String getMessage(){
        return errorCodeEnum.getMessage();
    }

    public String getErrorCode(){
        return errorCodeEnum.getErrorCode();
    }

}
