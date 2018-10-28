package com.qskx.springbootsync.vola;

/**
 * @author 111111
 * @date 2018-10-27 14:24
 */
public class RunThread extends Thread {

    private volatile boolean isRunning = true;
    private void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public void run(){
        System.out.println("进入run方法..");
        int i = 0;
        while(isRunning == true){
            //..
        }
        System.out.println("线程停止");
    }

    public static void main(String[] args) throws InterruptedException {
        RunThread rt = new RunThread();
        rt.start();
        Thread.sleep(1000);
        rt.setRunning(false);
        System.out.println("isRunning的值已经被设置了false");
    }
}
