package com.lifeng.web.api.aspect;

import com.google.common.base.Joiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifeng on 2018/5/17.
 * 自定义日志切面
 */
@Aspect
@Component
public class LoggerAspect {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Pointcut(value = "@annotation(com.lifeng.web.api.annotation.Logger)")
    public void performance(){
    }
    @Around("performance()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //得到HttpServletRequest对象
        HttpServletRequest request = getRequest();
        long startTimeMillis = System.currentTimeMillis();
        //得到自定义注解的值集合
        String[] annotationValue = getLoggerAnnotationValue(joinPoint);
        Object result = "";
        /** 执行目标方法 */
        try{
            result= joinPoint.proceed();
            long took = (System.currentTimeMillis() - startTimeMillis);
            log.info("module=" + annotationValue[0] + ";methodName=" + annotationValue[1] + ";requestParam=" + getRequestParam(request) + ";resultString=" + result.toString()  + ";tookTime=" + took + "毫秒");
        }
        catch(Exception e){
            log.error("日志记录发生错误, errorMessage: {}", e.getMessage());
        }
        return result;
    }

    /**
     * 得到HttpServletRequest对象
     * @return
     */
    private HttpServletRequest getRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }
    /**
     * 得到请求的参数
     * @return
     */
    private String getRequestParam(HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            result.put(name,value);
        }
        Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("=");
        return mapJoiner.join(result);
    }

    /**
     * 得到注解的值
     * @param joinPoint
     * @return
     */
    private String[] getLoggerAnnotationValue(ProceedingJoinPoint joinPoint){
        String[] result = new String[2];
        //得到对一个类
        String targetName = joinPoint.getTarget().getClass().getName();
        //得到切面方法
        String methodName = joinPoint.getSignature().getName();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                com.lifeng.web.api.annotation.Logger logger = method.getAnnotation(com.lifeng.web.api.annotation.Logger.class);
                if(null != logger){
                    result[0] = logger.module();
                    result[1] = logger.methodName();
                }
                break ;
            }
        }
        return result;
    }
}
