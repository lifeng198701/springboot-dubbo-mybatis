package com.lifeng.commons.cache.redis;

/**
 * Jedis配置型
 * 分片的redis
 */
public class JedisConfiguration {
    //多个redis服务,格式127.0.0.1:6379,192.168.5.20:6379
    private final String servers;

    private final Integer maxTotal;

    private final Integer maxIdle;

    private final Long maxWaitMillis;

    public JedisConfiguration(String servers, Integer maxTotal, Integer maxIdle, Long maxWaitMillis) {
        this.servers = servers;
        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.maxWaitMillis = maxWaitMillis;
    }
    public Integer getMaxTotal() {
        return maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }


    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public String getServers() {
        return servers;
    }

}
