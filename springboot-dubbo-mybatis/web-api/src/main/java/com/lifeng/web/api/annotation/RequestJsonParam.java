package com.lifeng.web.api.annotation;

import java.lang.annotation.*;

/**
 * 传递过来的字符串信息,直接转化成对象
 * 类似于RequestParam注解
 * 
 * @author lifeng
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonParam
{

    /**
     * 用于绑定的请求参数名字
     */
    String value() default "";

    /**
     * 是否必须，默认是
     */
    boolean required() default true;

    /**
     * 满足行情bt需求，取json中某个path后的内容
     */
    String fieldName() default "";
}
