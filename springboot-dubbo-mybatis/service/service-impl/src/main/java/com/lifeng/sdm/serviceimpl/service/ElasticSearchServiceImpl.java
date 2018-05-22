package com.lifeng.sdm.serviceimpl.service;

import com.google.common.collect.Lists;
import com.lifeng.commons.elasticsearch.ElasticSearchClient;
import com.lifeng.commons.json.JsonConverter;
import com.lifeng.sbm.serviceapi.pojo.ElasticUser;
import com.lifeng.sbm.serviceapi.service.ElasticSearchService;
import com.lifeng.sdm.serviceimpl.contants.SearchConstant;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lifeng on 2018/5/22.
 *
 */
@Service("elasticSearchService")
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Resource
    private ElasticSearchClient elasticSearchClient;

    @Override
    public List<ElasticUser> getALLElasticUser() {
        BoolQueryBuilder matchQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        // 搜索请求
        SearchRequestBuilder srb = elasticSearchClient.getClient().prepareSearch(SearchConstant.INDEX).setTypes(SearchConstant.TYPE_STOCK);
        srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse response = srb.setQuery(matchQueryBuilder).setFrom(0)
                .setSize(10).addSort("_score", SortOrder.DESC)
                .execute().actionGet();
        SearchHits hits = response.getHits();
        List<ElasticUser> result = Lists.newArrayList();
        for (SearchHit searchHit : hits)
        {

            String source = searchHit.getSourceAsString();
            result.add(JsonConverter.parse(source,ElasticUser.class));
        }
        return result;
    }

    /**
     * 新增索引
     * 真正的项目一般都是定时器定时增量去调用该服务接口,创建索引
     * @return
     */
    @Override
    public boolean addIndex() {
        logger.info("addIndex--------------------------");
        boolean flag = true;
        // 批量接口
        BulkRequestBuilder bulkRequest = elasticSearchClient.getClient().prepareBulk();
        // 文档主键getAssetId
        IndexRequestBuilder indexReq = elasticSearchClient.getClient().prepareIndex(SearchConstant.INDEX,SearchConstant.TYPE_STOCK)
                .setId("2").setSource("code",
                        "code002", "name", "wangwu", "sex",
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
        return flag;
    }
}
