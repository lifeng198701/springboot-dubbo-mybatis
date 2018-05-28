package com.lifeng.commons.exception;

/**
 * Created by lifeng on 2018/5/28.
 * 错误对象,包括返回码和错误描述
 */
public class ErrorMsg {

    private final int errorCode;

    private final String errorMsg;

    public ErrorMsg(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

}
