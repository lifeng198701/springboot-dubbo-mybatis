package com.lifeng.web.api.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lifeng on 2018/5/21.
 */
@Component
public class RedisConfig {
    //多个redis服务,格式127.0.0.1:6379,192.168.5.20:6379
    @Value("${jedis.servers}")
    private String servers;
    @Value("${jedis.max.total}")
    private Integer maxTotal;

    @Value("${jedis.max.idle}")
    private Integer maxIdle;

    @Value("${jedis.max.waitmillis}")
    private Long maxWaitMillis;

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }
}
