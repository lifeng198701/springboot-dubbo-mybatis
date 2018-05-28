package com.lifeng.web.api.exception;

import com.lifeng.commons.exception.BusinessException;
import com.lifeng.commons.exception.ErrorCodeEnum;
import com.lifeng.commons.exception.ErrorMsg;

/**
 * 认证异常
 */
public class AuthException extends BusinessException {
    public AuthException(ErrorMsg errorCodeEnum){
        super(errorCodeEnum);
    }
}
