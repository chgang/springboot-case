package com.qskx.springbootsync.sync;

import java.util.concurrent.TimeUnit;

/**
 * 字符串常量做为锁对象
 * @author 111111
 * @date 2018-10-27 13:41
 */
public class StringLock {

    public void method() {
        //new String("字符串常量")
        synchronized (new String("字符串常量")) {
            try {
                while(true){
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + "开始");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + "结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final StringLock stringLock = new StringLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
