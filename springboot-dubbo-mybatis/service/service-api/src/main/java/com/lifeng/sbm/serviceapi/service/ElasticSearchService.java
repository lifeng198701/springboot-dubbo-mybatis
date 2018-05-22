package com.lifeng.sbm.serviceapi.service;

import com.lifeng.sbm.serviceapi.pojo.ElasticUser;

import java.util.List;

/**
 * Created by lifeng on 2018/5/22.
 * elasticSearch服务
 */
public interface ElasticSearchService {

    List<ElasticUser> getALLElasticUser();

    boolean addIndex();


}
