package com.qskx.springbootsync.sync;

import java.util.concurrent.TimeUnit;

/**
 * 死锁问题
 * @author 111111
 * @date 2018-10-27 13:58
 */
public class DeadLock implements Runnable {

    private String tag;
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public void setTag(String tag){
        this.tag = tag;
    }

    @Override
    public void run() {
        if(tag.equals("a")){
            synchronized (lock1) {
                try {
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock1执行");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock2执行");
                }
            }
        }
        if(tag.equals("b")){
            synchronized (lock2) {
                try {
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock2执行");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 进入lock1执行");
                }
            }
        }
    }

    public static void main(String[] args) {

        DeadLock d1 = new DeadLock();
        d1.setTag("a");
        DeadLock d2 = new DeadLock();
        d2.setTag("b");

        Thread t1 = new Thread(d1, "t1");
        Thread t2 = new Thread(d2, "t2");

        t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
