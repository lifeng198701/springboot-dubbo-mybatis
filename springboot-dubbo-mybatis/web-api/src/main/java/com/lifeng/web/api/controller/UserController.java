package com.lifeng.web.api.controller;

import com.lifeng.commons.cache.redis.RedisClient;
import com.lifeng.commons.elasticsearch.ElasticSearchClient;
import com.lifeng.commons.web.BaseController;
import com.lifeng.commons.web.FrameResponse;
import com.lifeng.sbm.serviceapi.pojo.User;
import com.lifeng.sbm.serviceapi.service.UserService;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifeng on 2018/5/21.
 */
@RestController
public class UserController extends BaseController{

    @Resource
    private UserService userService;

    @Resource
    private RedisClient redisClient;

    @Resource
    ElasticSearchClient elasticSearchClient;

    @RequestMapping("/getAllUser")
    public FrameResponse getAllUser(){
        TransportClient client = elasticSearchClient.getClient();
        List<User> list = userService.getAllUser();
        redisClient.set("fengzai","fengzai");
        return buildSuccessResponse(list).getResponse();
    }

}
