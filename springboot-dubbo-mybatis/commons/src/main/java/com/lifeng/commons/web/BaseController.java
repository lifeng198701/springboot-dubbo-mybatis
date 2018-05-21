package com.lifeng.commons.web;


import com.lifeng.commons.contant.ReturnCodeContant;

/**
 * Created by lifeng on 2018/5/17.
 */
public class BaseController {
    /**
     * 构建Response对象
     * @return
     */
    protected FrameResponseBuilder buildResponse(){
       return new FrameResponseBuilder();
    }

    /**
     * 构建统一成功响应对象
     * @param data
     * @return
     */
    protected FrameResponseBuilder buildSuccessResponse(Object data){
        return new FrameResponseBuilder().buildCode(ReturnCodeContant.SUCCESSCODE).buildData(data);
    }

    /**
     * 构建统一失败响应对象
     * @param message
     * @return
     */
    protected FrameResponseBuilder buildFailResponse(String message){
        return new FrameResponseBuilder().buildCode(ReturnCodeContant.FAILCODE).buildMessage(message);
    }




}
