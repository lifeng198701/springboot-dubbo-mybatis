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

    private final CuratorFramework client;
    private final String path;
    private final long lockTimeoutMs;
    private final InterProcessSemaphoreMutex lock;

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    public DistributedLock(CuratorFramework client, String path, long lockTimeoutMs) {
        this.client = client;
        this.path = path;
        this.lockTimeoutMs = lockTimeoutMs;
        this.lock = new InterProcessSemaphoreMutex(client, path);

    }

    public void acquireLock() {
        try {
            boolean acquired = lock.acquire(lockTimeoutMs, TimeUnit.MILLISECONDS);
            if (!acquired) {
                LOGGER.error("could not acquire the lock within {} ms", lockTimeoutMs);
            }
        } catch(Exception e) {
            LOGGER.error("Exception while acquiring the lock: {}", e);
        }
    }

    public void releaseLock() {
        try {
            lock.release();
        } catch (Exception e) {
            LOGGER.error("Exception while releasing the lock: {}", e);
        }
    }

    public void lockedBlock(Runnable runnable) {
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
