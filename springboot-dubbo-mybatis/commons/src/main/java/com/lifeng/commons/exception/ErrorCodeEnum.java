package com.lifeng.commons.exception;

/**
 * 错误码枚举
 * add by lifeng
 */
public enum ErrorCodeEnum {
    //参数错误
    PARAMERROR("522222","参数错误"),DEFAULT("952685","系统内部异常"),AUTHERROR("99999","认证失败");
    private String errorCode;
    private String message;
    ErrorCodeEnum(String errorCode,String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
    /**
     * 根据key获取value
     *
     * @param key
     *            : 键值key
     * @return String
     */
    public static String getValueByKey(String key) {
        ErrorCodeEnum[] enums = ErrorCodeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getErrorCode().equals(key)) {
                return enums[i].getMessage();
            }
        }
        return "";
    }

}
