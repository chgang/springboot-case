package decompile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: springboot-case
 * @Package: decompile
 * @ClassName: DeadLock
 * @Description:
 * @Author: 111111
 * @CreateDate: 2019/2/21 17:41
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class DeadLock {

    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();

        ExecutorService ex = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            int order = i % 2 == 0 ? 1 : 0;
            ex.execute(new TestTask(order, obj1, obj2));
        }

    }


    static class TestTask implements Runnable {
        private Object obj1;
        private Object obj2;
        private int order;

        public TestTask(int order, Object obj1, Object obj2) {
            this.order = order;
            this.obj1 = obj1;
            this.obj2 = obj2;
        }

        public void test1() throws InterruptedException {
            synchronized (obj1) {

                Thread.yield();
                synchronized (obj2) {
                    System.out.println("test1 >>>");
                }

            }
        }

        public void test2() throws InterruptedException {
            synchronized (obj2) {
                Thread.yield();
                synchronized (obj1) {
                    System.out.println("test2 >>>>");
                }

            }
        }

        @Override
        public void run() {

            while (true) {
                try {
                    if (this.order == 1) {
                        this.test1();
                    } else {
                        this.test2();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}