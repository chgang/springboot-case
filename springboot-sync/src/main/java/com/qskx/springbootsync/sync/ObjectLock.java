package com.qskx.springbootsync.sync;

import java.util.concurrent.TimeUnit;

/**
 * 对象锁，类锁
 * @author 111111
 * @date 2018-10-27 13:24
 */
public class ObjectLock {
    public void method1(){
        synchronized (this) {	//对象锁
            try {
                System.out.println("do method1..");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void method2(){		//类锁
        synchronized (ObjectLock.class) {
            try {
                System.out.println("do method2..");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object lock = new Object();
    public void method3(){		//任何对象锁
        synchronized (ObjectLock.class) {
            try {
                System.out.println("do method3..");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        final ObjectLock objLock = new ObjectLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                objLock.method1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                objLock.method2();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                objLock.method3();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
