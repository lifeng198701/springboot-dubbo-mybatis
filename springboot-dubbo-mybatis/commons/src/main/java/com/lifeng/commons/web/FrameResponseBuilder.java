package com.lifeng.commons.web;

/**
 * Created by lifeng on 2018/5/16.
 * 构建者模式,构建相应对象
 */
public class FrameResponseBuilder {
    FrameResponse response;
    public FrameResponseBuilder(){
        response = new FrameResponse();
    }
    public FrameResponseBuilder buildCode(String code){
        response.setCode(code);
        return this;
    }
    public  FrameResponseBuilder buildMessage(String message){
        response.setMessage(message);
        return this;
    }
    public  FrameResponseBuilder buildData(Object data){
        response.setData(data);
        return this;
    }

   public FrameResponse getResponse(){
        return response;
   }

}
