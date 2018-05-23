package com.lifeng.commons.zookeeper;

import com.google.common.base.Joiner;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lifeng on 2018/5/16.
 */
public class DefaultZkClient implements ZkClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultZkClient.class);

    CuratorFramework curatorFramework;
    public DefaultZkClient(String connectString) {
        curatorFramework = CuratorFrameworkFactory.newClient(connectString, RETRY_INFINITY);
        start();
    }

    public DefaultZkClient(String connectString, String namespace) {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString).namespace(namespace).retryPolicy(RETRY_INFINITY).build();
        start();
    }

    public DefaultZkClient(String connectString, String namespace, String username, String password) {
        String auth = Joiner.on(":").skipNulls().join(username, password);
        curatorFramework = CuratorFrameworkFactory.builder().connectString(connectString).namespace(namespace)
                .authorization(SCHEME_DIGEST, auth.getBytes()).aclProvider(CREATOR_ALL_ACL_PROVIDER).retryPolicy(RETRY_INFINITY).build();
        start();
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    @Override
    public void start() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        addConnectionStateListener(new ConnectionStateListener() {
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.CONNECTED) {
                    countDownLatch.countDown();
                }
            }
        });
        ///异步,可以根据ConnectionStateListener得到连接状态
        curatorFramework.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.warn("await curatorFramework start interrupted", e);
        }
    }

    @Override
    public void create(String path) throws Exception {
        create(path, CreateMode.PERSISTENT, DEFAULT_DATA);
    }

    @Override
    public void createEphemeral(String path) throws Exception {
        create(path, CreateMode.EPHEMERAL, DEFAULT_DATA);
    }

    @Override
    public void create(String path, CreateMode createMode, byte[] data) throws Exception {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, data);
        } catch (KeeperException.NodeExistsException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    @Override
    public Stat setData(String path, byte[] data) throws Exception {
        return setData(path, data, DEFAULT_VERSION);
    }

    @Override
    public Stat setData(String path, byte[] data, int version) throws Exception {
        try {
            return curatorFramework.setData().withVersion(version).forPath(path, data);
        } catch (KeeperException.NoNodeException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (KeeperException.BadVersionException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (KeeperException.NoAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void delele(String path) throws Exception {
        delele(path, DEFAULT_VERSION);
    }

    @Override
    public void delele(String path, int version) throws Exception {
        try {
            curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).forPath(path);
        } catch (KeeperException.NoNodeException e) {
            LOGGER.error(e.getMessage());
        } catch (KeeperException.BadVersionException e) {
            LOGGER.error(e.getMessage());
        } catch (KeeperException.NoAuthException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public byte[] getData(String path) throws Exception {
        try {
            return curatorFramework.getData().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (KeeperException.NoAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String getStringData(String path) throws Exception {
        byte[] data = getData(path);
        return data == null ? EMPTY_STRING : new String(getData(path));
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        try {
            return curatorFramework.getChildren().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } catch (KeeperException.NoAuthException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean checkExist(String path) throws Exception {
        return stat(path) != null;
    }

    @Override
    public Stat stat(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path);
    }

    @Override
    public void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        curatorFramework.getConnectionStateListenable().addListener(connectionStateListener);
    }



    @Override
    public void close() {
        CloseableUtils.closeQuietly(curatorFramework);
    }
}
