package com.lifeng.web.api.exception;

import com.lifeng.commons.exception.BusinessException;
import com.lifeng.commons.exception.ErrorCodeEnum;
import com.lifeng.commons.exception.ErrorMsg;

/**
 * Created by lifeng on 2018/5/17.
 * 参数异常
 */
public class ParamException extends BusinessException {
    public ParamException(ErrorMsg errorMsg){
        super(errorMsg);
    }
}
