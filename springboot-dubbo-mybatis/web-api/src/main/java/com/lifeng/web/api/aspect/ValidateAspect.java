package com.lifeng.web.api.aspect;

import com.lifeng.commons.exception.ErrorMsg;
import com.lifeng.commons.util.StringUtil;
import com.lifeng.commons.validate.ValidatorUtil;
import com.lifeng.web.api.contants.ErrorCode;
import com.lifeng.web.api.exception.ParamException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by lifeng on 2018/5/17.
 * 参数校验切面
 */
@Aspect
@Component
public class ValidateAspect {
    @Pointcut(value = "@annotation(com.lifeng.web.api.annotation.Validate)")
    public void performance(){
    }

    @Around("performance()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object object : args){
           String errorMsgs =  ValidatorUtil.validate(object);
            if(StringUtil.isNotEmpty(errorMsgs)){
                throw new ParamException(new ErrorMsg(ErrorCode.PARAM_ERROR,errorMsgs));
            }
        }
        return joinPoint.proceed();
    }
}
