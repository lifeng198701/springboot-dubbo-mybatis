package com.lifeng.commons.zookeeper.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;

/**
 * Created by lifeng on 17/11/16.
 * zk選舉
 */
public class LeaderSelector extends LeaderSelectorListenerAdapter {

    private final org.apache.curator.framework.recipes.leader.LeaderSelector leaderSelector;

    public LeaderSelector(CuratorFramework curatorFramework, String leaderPath, String hostIp) {
        leaderSelector = new org.apache.curator.framework.recipes.leader.LeaderSelector(curatorFramework, leaderPath, this);
        leaderSelector.setId(hostIp);
        //保此实例在释放领导权后还可能获得领导权
        leaderSelector.autoRequeue();
    }


    public void start() {
        leaderSelector.start();
    }

    /**
     * 主备选举就按照这个来
     * @param curatorFramework
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        System.out.println(leaderSelector.getId() + " own leader");
        Thread.sleep(1000 * 5);
        System.out.println(leaderSelector.getId() + " lost leader");
//        leaderSelector.close();
//        curatorFramework.close();
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        if (newState == ConnectionState.SUSPENDED || newState == ConnectionState.LOST) {

            System.out.println(leaderSelector.getId() + "lost");
        }
    }

    public void close() {
        leaderSelector.close();
    }
}
