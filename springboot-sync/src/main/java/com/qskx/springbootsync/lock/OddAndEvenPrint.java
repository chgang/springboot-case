package com.qskx.springbootsync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 111111
 * @date 2019-02-16 14:25
 */
public class OddAndEvenPrint {

    private int num = 100;
    public void oddPrint() {
        for (int i = 0; i < 100; i++) {
            synchronized (this) {
                this.notify();
                if (i % 2 != 0) {
                    System.out.print(" " + i);
                }
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Thread.currentThread().interrupt();
    }

    public void evenPrint() {
        for (int i = 0; i < 100; i++) {
            synchronized (this) {
                this.notify();
                if (i % 2 == 0) {
                    System.out.print(" " + i);
                }
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println("线程打断 >>>>>>>>");;
                }
                if (i == 98) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
    private volatile boolean flag = true;
    public void volatileEvenTest() {
        for (int i = 0; i < 100; i++) {
            if (flag == true && i % 2 == 0) {
                System.out.print(i + " ");
                flag = false;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void volatileOddTest() {
        for (int i = 0; i < 100; i++) {
            if (flag == false && i % 2 != 0) {
                System.out.print(i + " ");
                flag = true;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Lock lock = new ReentrantLock();
    private static Condition oddCondition = lock.newCondition();
    private static Condition evenCondition = lock.newCondition();

    public void conditionOdd() {
        lock.lock();
        try {
            for (int i = 0; i < 100; i++) {
                evenCondition.signal();
                if (i % 2 != 0) {
                    System.out.print(i + " ");
                }
                oddCondition.await();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void conditionEven() {
        lock.lock();
        try {
            for (int i = 0; i < 100; i++) {
                oddCondition.signal();
                if (i % 2 == 0) {
                    System.out.print(i + " ");
                }
                evenCondition.await();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        OddAndEvenPrint oep = new OddAndEvenPrint();
        Thread thread_1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                oep.oddPrint();
//                oep.volatileEvenTest();
                oep.conditionOdd();
            }
        });

        Thread thread_2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                oep.evenPrint();
//                oep.volatileOddTest();
                oep.conditionEven();
            }
        });

        thread_1.start();
        thread_2.start();
        try {
            thread_1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread_2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n >>>>>>>>>>>>>");
    }
}
