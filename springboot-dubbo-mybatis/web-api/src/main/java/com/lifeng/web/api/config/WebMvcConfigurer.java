package com.lifeng.web.api.config;

import com.lifeng.commons.cache.redis.JedisConfiguration;
import com.lifeng.commons.cache.redis.RedisClient;
import com.lifeng.commons.concurrency.lock.redis.RedisReentrantLock;
import com.lifeng.commons.concurrency.lock.zookeeper.DistributedLock;
import com.lifeng.commons.elasticsearch.ElasticSearchClient;
import com.lifeng.commons.zookeeper.DefaultZkClient;
import com.lifeng.web.api.properties.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Created by lifeng on 2018/5/17.
 *
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private RedisConfig redisConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //新增拦截器
//        registry.addInterceptor(new AuthHandlerInterceptor()).addPathPatterns("/*");
    }
    /**
     * 初始化jedis对象
     * @return
     */
    @Bean
    public RedisClient initRedisClient() {
        JedisConfiguration jedisConfiguration = new JedisConfiguration(redisConfig.getServers(),redisConfig.getMaxTotal(),redisConfig.getMaxIdle(),redisConfig.getMaxWaitMillis());
        return new RedisClient(jedisConfiguration);
    }

    /**
     * redis分布式锁的对象
     */
    @Bean
    public RedisReentrantLock initRedisReentrantLock(){
        return new RedisReentrantLock(initRedisClient(),5000,5000);
    }

    /**
     * 初始化jedis配置项目对象
     * @return
     */
    @Bean
    public ElasticSearchClient initElasticSearchClient() {
        ElasticSearchClient elasticSearchClient = new ElasticSearchClient("192.168.11.132:9300","es-cluster");
        return elasticSearchClient;
    }

    /**
     * zk分布式锁
     * @return
     */
    @Bean
    public DistributedLock initDistributedLock(){
        return new DistributedLock(initZkClient().getCuratorFramework(),"/lock",5000);
    }

    @Bean
    public DefaultZkClient initZkClient(){
        return new DefaultZkClient("127.0.0.1:2181","mynamespace");
    }



}
