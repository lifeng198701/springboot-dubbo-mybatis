package com.lifeng.sdm.serviceimpl.config;

import com.lifeng.commons.elasticsearch.ElasticSearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lifeng on 2018/5/17.
 *
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //新增拦截器
//        registry.addInterceptor(new AuthHandlerInterceptor()).addPathPatterns("/*");
    }

    /**
     * 初始化jedis配置项目对象
     * @return
     */
    @Bean
    public ElasticSearchClient getElasticSearchClient() {
        ElasticSearchClient elasticSearchClient = new ElasticSearchClient("192.168.11.132:9300","es-cluster");
        return elasticSearchClient;
    }



}
