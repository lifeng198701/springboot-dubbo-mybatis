package com.lifeng.web.api.exception;

import com.lifeng.commons.exception.BusinessException;
import com.lifeng.commons.exception.ErrorCodeEnum;
import com.lifeng.commons.web.FrameResponse;

import com.lifeng.commons.web.FrameResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常统一处理
 */
@RestControllerAdvice
public class ExceptionHander {

    private final static Logger log = LoggerFactory.getLogger(ExceptionHander.class);

    @ExceptionHandler(value = Exception.class)
    public FrameResponse errorHandler(HttpServletRequest reqest,
                                      HttpServletResponse response, Exception e) throws Exception {
        boolean error_level = true;
        int errorCode = -1;
        String message = "系统内部异常";
        if(e instanceof BusinessException){
            BusinessException paramException = (BusinessException)e;
            errorCode = paramException.getErrorCode();
            message = paramException.getMessage();
            error_level = false;
        }
        if (error_level){
            log.error(e.toString());
        }
        return new FrameResponseBuilder().buildCode(String.valueOf(errorCode))
                .buildMessage(message).getResponse();
    }

    /**
     *
     * @Title: IMoocExceptionHandler.java
     * @Package com.imooc.exception
     * @Description: 判断是否是ajax请求
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     *
     * @author leechenxiang
     * @date 2017年12月3日 下午1:40:39
     * @version V1.0
     */
    private static boolean isAjax(HttpServletRequest httpRequest){
        return  (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals( httpRequest.getHeader("X-Requested-With").toString()) );
    }



}
