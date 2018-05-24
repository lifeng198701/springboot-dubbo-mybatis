package com.lifeng.web.api.controller;
import com.lifeng.commons.cache.redis.RedisClient;
import com.lifeng.commons.concurrency.lock.redis.RedisReentrantLock;
import com.lifeng.commons.concurrency.lock.zookeeper.DistributedLock;
import com.lifeng.commons.elasticsearch.ElasticSearchClient;
import com.lifeng.commons.web.BaseController;
import com.lifeng.commons.web.FrameResponse;
import com.lifeng.sbm.serviceapi.pojo.User;
import com.lifeng.sbm.serviceapi.service.UserService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lifeng on 2018/5/21.
 */
@RestController
public class UserController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisClient redisClient;

    @Resource
    private RedisReentrantLock redisReentrantLock;

    @Resource
    private ElasticSearchClient elasticSearchClient;

    @Resource
    private DistributedLock distributedLock;


    @RequestMapping("/getAllUser")
    public FrameResponse getAllUser(){
        TransportClient client = elasticSearchClient.getClient();
        addIndex();
        List<User> list = userService.getAllUser();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

//        executorService.submit(() ->{
//            try {
//                long a = redisReentrantLock.lock("lifeng");
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + a);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        executorService.submit(() ->{
//            try {
//                long b = redisReentrantLock.lock("lifeng");
//                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + b);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        });

        try {
            distributedLock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("dddddddddddddddddd");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            distributedLock.releaseLock();
        }

        try {
            distributedLock.acquireLock();
            System.out.println("mmmmmmmmmmmmmmmmmm");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        redisClient.set("fengzai","fengzai");
        return buildSuccessResponse(list).getResponse();
    }


    private void addIndex(){
        // 批量接口
        BulkRequestBuilder bulkRequest = elasticSearchClient.getClient().prepareBulk();
        // 文档主键getAssetId
        IndexRequestBuilder indexReq = elasticSearchClient.getClient().prepareIndex("mydb","mytable")
                .setId("1").setSource("id", "1", "code",
                        "code001", "name", "lifeng", "sex",
                        "男", "address", "湖北,孝感");
        bulkRequest.add(indexReq);
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        // 执行失败的记录输出
        Arrays.asList(bulkResponse.getItems())
                .stream().forEach(item -> {
            if (item.isFailed()) {
                logger.info("ES执行失败的资产:{}， 失败的原因：{}", item.getId(), item.getFailureMessage());
            }
        });

    }

}
