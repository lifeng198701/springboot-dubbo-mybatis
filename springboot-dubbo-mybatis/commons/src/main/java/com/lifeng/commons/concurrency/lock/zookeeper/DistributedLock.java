package com.lifeng.commons.concurrency.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by lifeng on 2018/5/23.
 */
public class DistributedLock {

    public static final String BZ_PATH = "/BZ_LOCK/";
    private final CuratorFramework client;
    private final String path;
    private final long lockTimeoutMs;
    private final InterProcessSemaphoreMutex lock;

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    public DistributedLock(CuratorFramework client, String path, long lockTimeoutMs) {
        this.client = client;
        this.path = BZ_PATH + path;
        this.lockTimeoutMs = lockTimeoutMs;
        this.lock = new InterProcessSemaphoreMutex(client, path);

    }

    /**
     * 一直阻塞的分布式锁
     * @throws InterruptedException
     */
    public void acquireLock() throws InterruptedException{
        try
        {
            lock.acquire();
        }
        catch (InterruptedException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 有超时时间的分布式锁
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException
    {
        try
        {
            return lock.acquire(timeout, unit);
        }
        catch (InterruptedException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void releaseLock() {
        try {
            lock.release();
        } catch (Exception e) {
            LOGGER.error("Exception while releasing the lock: {}", e);
        }
    }

    public void lockedBlock(Runnable runnable) throws InterruptedException {
        acquireLock();
        try {
            runnable.run();
        } finally {
            releaseLock();
        }
    }

    public boolean isLocked() {
        return lock.isAcquiredInThisProcess();
    }
}
