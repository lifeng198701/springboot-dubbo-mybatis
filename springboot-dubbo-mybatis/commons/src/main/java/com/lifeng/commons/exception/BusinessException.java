package com.lifeng.commons.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    private final ErrorMsg errorMsg;
    public BusinessException(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getMessage(){
        return errorMsg.getErrorMsg();
    }

    public int getErrorCode(){
        return errorMsg.getErrorCode();
    }

}
