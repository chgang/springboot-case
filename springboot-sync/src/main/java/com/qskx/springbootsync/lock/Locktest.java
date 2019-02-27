package com.qskx.springbootsync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 111111
 * @date 2019-02-12 23:15
 */
public class Locktest {

    Lock lock = new ReentrantLock();
    public void outPut(String str){
        //上锁
        lock.lock();
        try{
            for(int i=0;i<str.length();i++){
                System.out.print(str.charAt(i));
            }
            System.out.print("\n");
        }finally {
            //解锁
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final Locktest example = new Locktest();
        new Thread(new Runnable() {
            @Override
            public void run() {

                    example.outPut("str1");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                    example.outPut("str2");
            }
        }).start();

    }
}
