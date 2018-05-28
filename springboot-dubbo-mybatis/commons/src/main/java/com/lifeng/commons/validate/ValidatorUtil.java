package com.lifeng.commons.validate;

import com.google.common.base.Joiner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lifeng on 2018/5/28.
 * 校验工具
 */
public class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    public static <T> String validate(T obj){
        Map<String,StringBuffer> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
        if(set != null && set.size() >0 ){
            errorMap = new HashMap<String,StringBuffer>();
            String property = null;
            for(ConstraintViolation<T> cv : set){
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if(errorMap.get(property) != null){
                    errorMap.get(property).append("," + cv.getMessage());
                }else{
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        if(null == errorMap){
            return "";
        }
        Joiner.MapJoiner mapJoiner = Joiner.on(";").withKeyValueSeparator("=");
        return mapJoiner.join(errorMap);
    }

    public static void main(String[] args) {

        User user = new User();
        user.setAge("41");
        user.setName("dddddddd");
        String errorMap = validate(user);
        System.out.println(errorMap);

//        errorMap.forEach((k,v) ->{
//            System.out.println(k + "----------" + v);
//        });





    }

    static class User{

        @NotBlank(message="用户名不能为空")
        private String name;

        @NotBlank(message="年龄不能为空")
        @Pattern(regexp="^[0-9]{1,2}$",message="年龄是整数")
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }


}
