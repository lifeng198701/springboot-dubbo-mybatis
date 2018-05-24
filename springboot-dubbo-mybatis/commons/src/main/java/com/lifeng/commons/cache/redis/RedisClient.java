package com.lifeng.commons.cache.redis;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lifeng.commons.json.JsonConverter;
import com.lifeng.commons.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.*;

/**
 * Created by lifeng on 2018/5/19.
 * 支持分片
 * 客户端在调用的时候只需要
 * 注入JedisConfiguration,对象就可以了
 */
//@Component
public class RedisClient implements DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ShardedJedisPool shardedJedisPool;

    //主要是取redis其中的一个节点的操作,例如消息订阅
    private JedisPool jedisPool;

    public RedisClient(JedisConfiguration jedisConfiguration){
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(jedisConfiguration.getMaxTotal());
            jedisPoolConfig.setMaxIdle(jedisConfiguration.getMaxIdle());
            jedisPoolConfig.setMaxWaitMillis(jedisConfiguration.getMaxWaitMillis());
            List<JedisShardInfo> shardList = new ArrayList<>();
            String servers = jedisConfiguration.getServers();
            String[] hosts = servers.split(",");
            for (int i = 0 ; i < hosts.length; i++){
                String host = hosts[i].split(":")[0];
                int port = Integer.parseInt(hosts[i].split(":")[1]);
                shardList.add(new JedisShardInfo(host,port));
            }
            // 发布订阅用，取分片中第一个节点
            JedisShardInfo shardInfo = shardList.get(0);
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(), 3000);
            this.shardedJedisPool =  new ShardedJedisPool(jedisPoolConfig, shardList);
            this.jedisPool = jedisPool;
        }catch (Exception e){
            logger.error(e.toString());
        }

    }


    public void set(byte[] key, byte[] value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        try {
            jedis.set(key, value);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public String set(String key, Object value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var4;
        try {
            var4 = jedis.set(key, JsonConverter.format(value));
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return var4;
    }

    public void set(String key, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        try {
            jedis.set(key, value);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public void del(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        try {
            jedis.del(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public Long setnx(String key, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.setnx(key, value);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public String setex(String key, int seconds, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var5;
        try {
            var5 = jedis.setex(key, seconds, value);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

//    public void publish(String channel, String message) {
//        Jedis jedis = this.jedisPool.getResource();
//
//        try {
//            jedis.publish(channel, message);
//        } catch (JedisConnectionException var8) {
//            logger.warn("", var8);
//            this.jedisPool.returnBrokenResource(jedis);
//            jedis = null;
//            throw var8;
//        } finally {
//            if(jedis != null) {
//                jedis.close();
//            }
//
//        }
//
//    }
//
//    public void subscribe(JedisPubSub pubSub, String... channels) {
//        Jedis jedis = this.jedisPool.getResource();
//
//        try {
//            jedis.subscribe(pubSub, channels);
//        } catch (JedisConnectionException var8) {
//            logger.warn("", var8);
//            this.jedisPool.returnBrokenResource(jedis);
//            jedis = null;
//            throw var8;
//        } finally {
//            if(jedis != null) {
//                jedis.close();
//            }
//
//        }
//
//    }
//
//    public void psubscribe(JedisPubSub pubSub, String... patterns) {
//        Jedis jedis = this.jedisPool.getResource();
//
//        try {
//            jedis.psubscribe(pubSub, patterns);
//        } catch (JedisConnectionException var8) {
//            logger.warn("", var8);
//            this.jedisPool.returnBrokenResource(jedis);
//            jedis = null;
//            throw var8;
//        } finally {
//            if(jedis != null) {
//                jedis.close();
//            }
//
//        }
//
//    }

    public String set(String key, String value, String nxxx, String expx, long time) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var8;
        try {
            var8 = jedis.set(key, value, nxxx, expx, time);
        } catch (JedisConnectionException var12) {
            logger.warn("", var12);
            jedis.close();
            jedis = null;
            throw var12;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var8;
    }

    public String lindex(String key, long index) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var5;
        try {
            var5 = jedis.lindex(key, index);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public String lset(String key, long index, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var6;
        try {
            var6 = jedis.lset(key, index, value);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public String getSet(String key, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var4;
        try {
            var4 = jedis.getSet(key, value);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long hset(String key, String field, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.hset(key, field, value);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long hlen(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.hlen(key);
        } catch (JedisConnectionException var7) {
            logger.warn("hlen String,  key:{}, field:{}", key, var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Boolean hexists(String key, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Boolean var4;
        try {
            var4 = jedis.hexists(key, field);
        } catch (JedisConnectionException var8) {
            logger.warn("hexists String,  key:{}, field:{}", new Object[]{key, field, var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Optional<String> hget(String key, String field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Optional var4;
        try {
            var4 = Optional.ofNullable(jedis.hget(key, field));
        } catch (JedisConnectionException var8) {
            logger.warn("hget String,  key:{}, field:{}", new Object[]{key, field, var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Map<String, String> hgetAll(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Map var3;
        try {
            var3 = jedis.hgetAll(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Optional<Set<String>> hkeys(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Optional var3;
        try {
            var3 = Optional.ofNullable(jedis.hkeys(key));
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long hdel(String key, String... field) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.hdel(key, field);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long hincrBy(String key, String field, long value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var6;
        try {
            var6 = jedis.hincrBy(key, field, value);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Optional<List<String>> hmget(String key, String... fields) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Optional var4;
        try {
            var4 = Optional.ofNullable(jedis.hmget(key, fields));
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public byte[] get(byte[] key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        byte[] var3;
        try {
            var3 = jedis.get(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public <T> T get(String key, Class<T> classz) {
        String json = this.get(key);
        return StringUtils.isEmpty(json) ? null : JsonConverter.parse(json, classz);
    }

    public String get(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var3;
        try {
            var3 = jedis.get(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long incrBy(String key, long value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.incrBy(key, value);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long decrBy(String key, long value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.decrBy(key, value);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long incr(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.incr(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }


    public void delByPipeline(List<String> keys) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        try {
            ShardedJedisPipeline pipeline = jedis.pipelined();
            Iterator var4 = keys.iterator();

            while (var4.hasNext()) {
                String key = (String) var4.next();
                pipeline.del(key);
            }

            pipeline.sync();
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    public List<Map<String, String>> hgetAllByPipeline(List<String> keys) {
        Preconditions.checkNotNull(keys);
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        try {
            ShardedJedisPipeline pipeline = jedis.pipelined();
            List<Response<Map<String, String>>> responses = Lists.newArrayListWithExpectedSize(keys.size());
            Iterator var5 = keys.iterator();

            while (var5.hasNext()) {
                String key = (String) var5.next();
                responses.add(pipeline.hgetAll(key));
            }

            pipeline.sync();
            List<Map<String, String>> rsltList = new ArrayList(keys.size());
            Iterator var14 = responses.iterator();

            while (var14.hasNext()) {
                Response<Map<String, String>> response = (Response) var14.next();
                rsltList.add(response.get());
            }

            return rsltList;
        } catch (JedisConnectionException var11) {
            logger.warn("", var11);
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    public void hmset(String key, Map<String, String> hash) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        try {
            jedis.hmset(key, hash);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public List<String> lrange(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        List var7;
        try {
            var7 = jedis.lrange(key, start, end);
        } catch (JedisConnectionException var11) {
            logger.warn("", var11);
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public long lpush(String key, String... values) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        long var4;
        try {
            var4 = jedis.lpush(key, values).longValue();
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public long rpush(String key, String... values) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        long var4;
        try {
            var4 = jedis.rpush(key, values).longValue();
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long expireAt(String key, long unixTime) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.expireAt(key, unixTime);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long expire(String key, int seconds) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.expire(key, seconds);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public boolean exists(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        boolean var3;
        try {
            var3 = jedis.exists(key).booleanValue();
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public long llen(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        long var3;
        try {
            var3 = jedis.llen(key).longValue();
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public String lpop(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var3;
        try {
            var3 = jedis.lpop(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public String rpop(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        String var3;
        try {
            var3 = jedis.rpop(key);
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public long lrem(String key, long count, String value) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        long var6;
        try {
            var6 = jedis.lrem(key, count, value).longValue();
        } catch (JedisConnectionException var11) {
            logger.warn("", var11);
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public long sadd(String key, String... elements) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        long var4;
        try {
            var4 = jedis.sadd(key, elements).longValue();
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        ScanResult var5;
        try {
            var5 = jedis.sscan(key, cursor, params);
        } finally {
            jedis.close();
        }

        return var5;
    }

    public long srem(String key, String... elements) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        long var4;
        try {
            var4 = jedis.srem(key, elements).longValue();
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public boolean sismember(String key, String element) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        boolean var4;
        try {
            var4 = jedis.sismember(key, element).booleanValue();
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Optional<Set<String>> smembers(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Optional var3;
        try {
            var3 = Optional.ofNullable(jedis.smembers(key));
        } catch (JedisConnectionException var7) {
            logger.warn("", var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long zadd(byte[] key, double score, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var6;
        try {
            var6 = jedis.zadd(key, score, member);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Long zadd(String key, double score, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var6;
        try {
            var6 = jedis.zadd(key, score, member);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zadd(key, scoreMembers);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zadd(key, scoreMembers);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zrem(byte[] key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrem(key, new byte[][]{member});
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zrem(String key, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrem(key, new String[]{member});
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zremrangeByRank(byte[] key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.zremrangeByRank(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long zremrangeByRank(String key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var5;
        try {
            var5 = jedis.zremrangeByRank(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long zremrangeByScore(byte[] key, double start, double end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var7;
        try {
            var7 = jedis.zremrangeByScore(key, start, end);
        } catch (JedisConnectionException var11) {
            logger.warn("", var11);
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var7;
        try {
            var7 = jedis.zremrangeByScore(key, start, end);
        } catch (JedisConnectionException var11) {
            logger.warn("", var11);
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Double zincrby(byte[] key, double score, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Double var6;
        try {
            var6 = jedis.zincrby(key, score, member);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Double zincrby(String key, double score, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Double var6;
        try {
            var6 = jedis.zincrby(key, score, member);
        } catch (JedisConnectionException var10) {
            logger.warn("", var10);
            jedis.close();
            jedis = null;
            throw var10;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var6;
    }

    public Long zrank(byte[] key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrank(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zrank(String key, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrank(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("", var8);
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Set<byte[]> zrange(byte[] key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrange(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("", var9);
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<String> zrange(String key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrange(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrange String,  key:{}, start:{}, end:{}", new Object[]{key, Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrangeWithScores(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrangeWithScores byte,  key:{}, start:{}, end:{}", new Object[]{StringUtil.getUTF8String(key), Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<Tuple> zrangeWithScores(String key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrangeWithScores(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrangeWithScores String,  key:{}, start:{}, end:{}", new Object[]{key, Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrangeByScore(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zrangeByScore byte,  key:{}, min:{}, max:{}", new Object[]{StringUtil.getUTF8String(key), Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrangeByScore(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zrangeByScore String,  key:{}, min:{}, max:{}", new Object[]{key, Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (JedisConnectionException var13) {
            logger.warn("zrangeByScore byte,  key:{}, min:{}, max:{}, offset:{}, count:{}", new Object[]{StringUtil.getUTF8String(key), Double.valueOf(min), Double.valueOf(max), Integer.valueOf(offset), Integer.valueOf(count), var13});
            jedis.close();
            jedis = null;
            throw var13;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var9;
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (JedisConnectionException var13) {
            logger.warn("zrangeByScore String,  key:{}, min:{}, max:{}, offset:{}, count:{}", new Object[]{key, Double.valueOf(min), Double.valueOf(max), Integer.valueOf(offset), Integer.valueOf(count), var13});
            jedis.close();
            jedis = null;
            throw var13;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var9;
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zrangeByScoreWithScores byte,  key:{}, min:{}, max:{}", new Object[]{StringUtil.getUTF8String(key), Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zrangeByScoreWithScores String,  key:{}, min:{}, max:{}", new Object[]{key, Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (JedisConnectionException var13) {
            logger.warn("zrangeByScoreWithScores byte,  key:{}, min:{}, max:{}, offset:{}, count:{}", new Object[]{StringUtil.getUTF8String(key), Double.valueOf(min), Double.valueOf(max), Integer.valueOf(offset), Integer.valueOf(count), var13});
            jedis.close();
            jedis = null;
            throw var13;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var9;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (JedisConnectionException var13) {
            logger.warn("zrangeByScoreWithScores String,  key:{}, min:{}, max:{}, offset:{}, count:{}", new Object[]{key, Double.valueOf(min), Double.valueOf(max), Integer.valueOf(offset), Integer.valueOf(count), var13});
            jedis.close();
            jedis = null;
            throw var13;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var9;
    }

    public Long zrevrank(byte[] key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrevrank(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("zrevrank byte,  key:{}, member:{}", new Object[]{StringUtil.getUTF8String(key), StringUtil.getUTF8String(member), var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long zrevrank(String key, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var4;
        try {
            var4 = jedis.zrevrank(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("zrevrank String,  key:{}, member:{}", new Object[]{key, member, var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Set<byte[]> zrevrange(byte[] key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrange(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrevrange byte,  key:{}, start:{}, end:{}", new Object[]{StringUtil.getUTF8String(key), Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrange(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrevrange String,  key:{}, start:{}, end:{}", new Object[]{key, Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrangeWithScores(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrevrangeWithScores byte,  key:{}, start:{}, end:{}", new Object[]{StringUtil.getUTF8String(key), Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrangeWithScores(key, (long) start, (long) end);
        } catch (JedisConnectionException var9) {
            logger.warn("zrevrangeWithScores String,  key:{}, start:{}, end:{}", new Object[]{key, Integer.valueOf(start), Integer.valueOf(end), var9});
            jedis.close();
            jedis = null;
            throw var9;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var5;
    }

    public Long zcard(byte[] key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();
        Long var3;
        try {
            var3 = jedis.zcard(key);
        } catch (JedisConnectionException var7) {
            logger.warn("zcard byte,  key:{}", StringUtil.getUTF8String(key), var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long scard(byte[] key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.scard(key);
        } catch (JedisConnectionException var7) {
            logger.warn("scard byte,  key:{}", StringUtil.getUTF8String(key), var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long scard(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.scard(key);
        } catch (JedisConnectionException var7) {
            logger.warn("scard string,  key:{}", key, var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long zcard(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.zcard(key);
        } catch (JedisConnectionException var7) {
            logger.warn("zcard String,  key:{}", key, var7);
            jedis.close();
            jedis = null;
            throw var7;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

    public Long zcount(byte[] key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var7;
        try {
            var7 = jedis.zcount(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zcount byte,  key:{}, min:{}, max:{}", new Object[]{StringUtil.getUTF8String(key), Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Long zcount(String key, double min, double max) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var7;
        try {
            var7 = jedis.zcount(key, min, max);
        } catch (JedisConnectionException var11) {
            logger.warn("zcount String,  key:{}, min:{}, max:{}", new Object[]{key, Double.valueOf(min), Double.valueOf(max), var11});
            jedis.close();
            jedis = null;
            throw var11;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var7;
    }

    public Double zscore(byte[] key, byte[] member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Double var4;
        try {
            var4 = jedis.zscore(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("zscore byte,  key:{}, member:{}", new Object[]{StringUtil.getUTF8String(key), StringUtil.getUTF8String(member), var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Double zscore(String key, String member) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Double var4;
        try {
            var4 = jedis.zscore(key, member);
        } catch (JedisConnectionException var8) {
            logger.warn("zscore String,  key:{}, member:{}", new Object[]{key, member, var8});
            jedis.close();
            jedis = null;
            throw var8;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var4;
    }

    public Long decr(String key) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var3;
        try {
            var3 = jedis.decr(key);
        } finally {
            jedis.close();
        }

        return var3;
    }

    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrevrangeByScore(key, max, min, offset, count);
        } finally {
            jedis.close();
        }

        return var7;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrevrangeByScore(key, max, min, offset, count);
        } finally {
            jedis.close();
        }

        return var9;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var9;
        try {
            var9 = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } finally {
            jedis.close();
        }

        return var9;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrangeByScoreWithScores(key, max, min);
        } finally {
            jedis.close();
        }

        return var5;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var7;
        try {
            var7 = jedis.zrevrangeByScore(key, max, min);
        } finally {
            jedis.close();
        }

        return var7;
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Set var5;
        try {
            var5 = jedis.zrevrangeByScore(key, max, min);
        } finally {
            jedis.close();
        }

        return var5;
    }

    public Long zremRangeByRank(String key, long start, long end) {
        ShardedJedis jedis = this.shardedJedisPool.getResource();

        Long var7;
        try {
            var7 = jedis.zremrangeByRank(key, start, end);
        } finally {
            jedis.close();
        }

        return var7;
    }

    @Override
    public void destroy() throws Exception {
        this.shardedJedisPool.close();
    }
}
