package com.qskx.springbootsync.lock.distribute;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.springbootsync.lock
 * @ClassName: DistributeLock
 * @Description: java类作用域描述
 * @Author: 111111
 * @CreateDate: 2019/2/27 13:38
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class DistributeLock implements Lock {

    private static final Logger LOG = LoggerFactory.getLogger(DistributeLock.class);

    private ZkClient zkClient;
    private String lockPath;
    private String currrentPath;
    private String beforePath;

    private volatile static int state;
    private volatile static Thread thread;

    public DistributeLock(String lockPath) {
        this.lockPath = lockPath;
        zkClient = new ZkClient("bzk1.fengjr.inc:2181,bzk2.fengjr.inc:2181,bzk3.fengjr.inc:2181");
        zkClient.setZkSerializer(new MyZkSerializer());

        if (!zkClient.exists(lockPath)) {
            try {
                zkClient.createPersistent(lockPath);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void lock() {

    }

    public boolean tryLock(int acquires) {
        Thread currentThread = Thread.currentThread();
        int state = getState();

        if (state == 0) {
            if (compareAndSetState(0, acquires, currentThread)) {
                return true;
            }
        } else if (currentThread == getThread()){
            int nextState = getState() + acquires;
            if (nextState < 0) {
                throw new RuntimeException("Maximum lock count exceeded.");
            }

            setState(nextState);
            LOG.info(String.format("%s :获取重入锁成功,当前获取锁次数: %s", Thread.currentThread().getName(), getState()));
            return true;
        }

        return false;
    }

    public boolean compareAndSetState(int expect, int update, Thread t) {
        if (this.currrentPath == null) {
            currrentPath = this.zkClient.createEphemeralSequential(lockPath + "/", 1);
        }

        List<String> children = this.zkClient.getChildren(lockPath);
        Collections.sort(children);

        if (getState() == expect && currrentPath.equals(lockPath + "/" + children.get(0))) {
            setState(update);
            DistributeLock.thread = t;
            LOG.info(String.format("%s :获取锁成功,当前获取锁次数: %s"),Thread.currentThread().getName(), getState());
            return true;
        }

        int currentIndex = children.indexOf(currrentPath.substring(lockPath.length() + 1));
        beforePath = lockPath + "/" + children.get(currentIndex -1);

        return false;

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        DistributeLock.state = state;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}