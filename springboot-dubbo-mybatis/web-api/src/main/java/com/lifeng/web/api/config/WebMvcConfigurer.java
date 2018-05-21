package com.lifeng.web.api.config;

import com.lifeng.commons.cache.redis.JedisConfiguration;
import com.lifeng.commons.cache.redis.RedisClient;
import com.lifeng.web.api.properties.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
     * 初始化jedis配置项目对象
     * @return
     */
    @Bean
    public JedisConfiguration getJedisConfiguration() {
        JedisConfiguration jedisConfiguration = new JedisConfiguration(redisConfig.getServers(),redisConfig.getMaxTotal(),redisConfig.getMaxIdle(),redisConfig.getMaxWaitMillis());
        return jedisConfiguration;
    }
    /**
     * 初始化jedis对象
     * @return
     */
    @Bean
    public ShardedJedisPool getJedisPool() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(getJedisConfiguration().getMaxTotal());
            jedisPoolConfig.setMaxIdle(getJedisConfiguration().getMaxIdle());
            jedisPoolConfig.setMaxWaitMillis(getJedisConfiguration().getMaxWaitMillis());
            List<JedisShardInfo> shardList = new ArrayList<>();
            String servers = getJedisConfiguration().getServers();
            String[] hosts = servers.split(",");
            for (int i = 0 ; i < hosts.length; i++){
                String host = hosts[i].split(":")[0];
                int port = Integer.parseInt(hosts[i].split(":")[1]);
                shardList.add(new JedisShardInfo(host,port));
            }
            return new ShardedJedisPool(jedisPoolConfig, shardList);
        } catch (Exception e) {
            throw new RuntimeException("无法加载资源文件!");
        }
    }

    /**
     * 初始化jedis对象
     * @return
     */
    @Bean
    public RedisClient getRedisClient() {
        return new RedisClient(getJedisPool());
    }



}
