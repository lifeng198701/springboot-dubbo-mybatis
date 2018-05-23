package com.lifeng.commons.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lifeng on 2018/5/16.
 */
public interface ZkClient {

    public static final RetryPolicy RETRY_INFINITY = new RetryNTimes(Integer.MAX_VALUE, (int) TimeUnit.SECONDS.toMillis(5));
    public static final String EMPTY_STRING = "";
    public static final byte[] DEFAULT_DATA = new byte[0];
    public static final int DEFAULT_VERSION = -1;
    public static final int EXPONENTIAL_BACKOFF_MAX_RETRIES = 29;
    public static final String SCHEME_DIGEST = "digest";
    public static final String SCHEME_IP = "ip";
    public static final String SCHEME_AUTH = "auth";
    public static final String SCHEME_WORLD = "world";

    public static final ACLProvider CREATOR_ALL_ACL_PROVIDER = new ACLProvider() {
        public List<ACL> getDefaultAcl() {
            return ZooDefs.Ids.CREATOR_ALL_ACL;
        }

        public List<ACL> getAclForPath(String path) {
            return ZooDefs.Ids.CREATOR_ALL_ACL;
        }
    };

    CuratorFramework getCuratorFramework();

    void start();

    void addConnectionStateListener(ConnectionStateListener connectionStateListener);

    void create(String path) throws Exception;

    void createEphemeral(String path) throws Exception;

    void create(String path, CreateMode createMode, byte[] data) throws Exception;

    Stat setData(String path, byte[] data) throws Exception;

    Stat setData(String path, byte[] data, int version) throws Exception;

    void delele(String path) throws Exception;

    void delele(String path, int version) throws Exception;

    byte[] getData(String path) throws Exception;

    String getStringData(String path) throws Exception;

    List<String> getChildren(String path) throws Exception;

    boolean checkExist(String path) throws Exception;

    Stat stat(String path) throws Exception;

    void close();
}
