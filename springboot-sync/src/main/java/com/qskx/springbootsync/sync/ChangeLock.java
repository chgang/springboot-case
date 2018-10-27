package com.qskx.springbootsync.sync;

import java.util.concurrent.TimeUnit;

/**
 * 锁对象改变问题
 * @author 111111
 * @date 2018-10-27 13:34
 */
public class ChangeLock {

    private String lock = "lock";

    private void method(){
        synchronized (lock) {
            try {
                System.out.println("当前线程 : "  + Thread.currentThread().getName() + "开始");
//                lock = "change lock";
                TimeUnit.SECONDS.sleep(3);
                System.out.println("当前线程 : "  + Thread.currentThread().getName() + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        final ChangeLock changeLock = new ChangeLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                changeLock.method();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                changeLock.method();
            }
        },"t2");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
