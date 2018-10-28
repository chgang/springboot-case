package com.qskx.springbootsync.sync;

/**
 * synchronized 异常处理
 * @author 111111
 * @date 2018-10-27 13:11
 */
public class SyncException {
    private int i = 0;
    public synchronized void operation(){
        while(true){
            try {
                i++;
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " , i = " + i);
                if(i == 20){
                    Integer.parseInt("a");
//                    throw new RuntimeException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO 打印日志
//                continue;
            }
        }
    }

    public static void main(String[] args) {

        final SyncException se = new SyncException();
        Thread t1 = new Thread(new Runnable() {

            public void run() {
                se.operation();
            }
        },"t1");
        t1.start();
    }

}
