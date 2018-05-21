package com.lifeng.web.api.exception;

import com.lifeng.commons.exception.BusinessException;
import com.lifeng.commons.exception.ErrorCodeEnum;

/**
 * Created by lifeng on 2018/5/17.
 * 参数异常
 */
public class ParamException extends BusinessException {
    public ParamException(ErrorCodeEnum errorCodeEnum){
        super(errorCodeEnum);
    }
}
