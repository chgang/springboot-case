package com.qskx.springbootsync.sync;

import java.util.concurrent.TimeUnit;

/**
 * 改变对象的属性不会影响锁的情况
 * @author 111111
 * @date 2018-10-27 13:48
 */
public class ModifyLock {

    private String name ;
    private int age ;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public synchronized void changeAttributte(String name, int age) {
        try {
            System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 开始");
            this.setName(name);
            this.setAge(age);

            System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 修改对象内容为： "
                    + this.getName() + ", " + this.getAge());

            TimeUnit.SECONDS.sleep(2);
            System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final ModifyLock modifyLock = new ModifyLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                modifyLock.changeAttributte("张三", 20);
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                modifyLock.changeAttributte("李四", 21);
            }
        },"t2");

        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
