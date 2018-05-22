package com.lifeng.web.api.controller;

import com.lifeng.commons.web.BaseController;
import com.lifeng.commons.web.FrameResponse;
import com.lifeng.sbm.serviceapi.pojo.ElasticUser;
import com.lifeng.sbm.serviceapi.service.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifeng on 2018/5/21.
 * 搜索引擎ElasticSearch控制层
 */
@RestController
public class ElasticSearchController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);

    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping("/getElasticUsers")
    public FrameResponse getAllUser(){
        List<ElasticUser> list = elasticSearchService.getALLElasticUser();
        return buildSuccessResponse(list).getResponse();
    }

    /**
     * 此请求只是作为一个例子,一般我们实际项目都是以定时器同步从DB定时去同步增量数据到es
     * @return
     */
    @RequestMapping("/addIndex")
    public FrameResponse addIndex(){
       boolean flag = elasticSearchService.addIndex();
       if(flag){
           return buildSuccessResponse("").getResponse();
       }else {
           return buildFailResponse("创建索引失败").getResponse();
       }
    }
}
