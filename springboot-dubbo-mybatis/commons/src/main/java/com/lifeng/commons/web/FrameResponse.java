package com.lifeng.commons.web;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lifeng.commons.json.JsonConverter;

/**
 * Created by lifeng on 2018/5/16.
 */
public class FrameResponse {

    private String code;

    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return JsonConverter.format(this);
    }
}
