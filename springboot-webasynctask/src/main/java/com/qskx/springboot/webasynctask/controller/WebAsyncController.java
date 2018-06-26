package com.qskx.springboot.webasynctask.controller;

import com.qskx.springboot.webasynctask.service.WebAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.TimeUnit;

/**
 * @author 111111
 * @date 2018-06-26 09:37
 */
@RestController
public class WebAsyncController {

    private final WebAsyncService webAsyncService;
    private final static String ERROR_MESSAGE = "Task error";
    private final static String TIME_MESSAGE = "Task timeout";

    @Autowired
    public WebAsyncController(WebAsyncService webAsyncService) {
        this.webAsyncService = webAsyncService;
    }

    //    @Autowired
//    private WebAsyncService webAsyncService;

    @GetMapping("/completion")
    @ResponseBody
    public WebAsyncTask<String> asyncTaskCompletion() {
        // 打印处理线程名
        System.out.println(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<String>(10 * 1000L, () -> {
            System.out.println(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间5s，不超时
            TimeUnit.MILLISECONDS.sleep(5 * 1000L);
            return webAsyncService.generateUUID();
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> System.out.println("任务执行完成"));
        System.out.println("继续处理其他事情");
        return asyncTask;
    }

    @GetMapping("/exception")
    public WebAsyncTask<String> asyncTaskException() {
        // 打印处理线程名
        System.out.println(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            System.out.println(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间5s，不超时
            TimeUnit.MILLISECONDS.sleep(5 * 1000L);
            throw new Exception(ERROR_MESSAGE);
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> System.out.println("任务执行完成"));
        asyncTask.onError(() -> {
            System.out.println("任务执行异常");
            return ERROR_MESSAGE;
        });

        System.out.println("继续处理其他事情");
        return asyncTask;
    }

    @GetMapping("/timeout")
    public WebAsyncTask<String> asyncTaskTimeout() {
        // 打印处理线程名
        System.out.println(String.format("请求处理线程：%s", Thread.currentThread().getName()));

        // 模拟开启一个异步任务，超时时间为10s
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            System.out.println(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            // 任务处理时间15s，超时
//            new Thread(() -> {//另起线程不再起作用
//                try {
//                    TimeUnit.SECONDS.sleep(15 * 1000L);
//                    System.out.println("超时之后是否还会继续");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
            TimeUnit.SECONDS.sleep(15 * 1000L);
            return TIME_MESSAGE;
        });

        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> System.out.println("任务执行完成"));
        asyncTask.onTimeout(() -> {
            System.out.println("任务执行超时");
            return TIME_MESSAGE;
        });

        System.out.println("继续处理其他事情");
        return asyncTask;
    }

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @GetMapping("/threadPool")
    public WebAsyncTask<String> asyncTaskThreadPool() {
        return new WebAsyncTask<String>(10 * 1000L, executor, () -> {
            System.out.println(String.format("异步工作线程：%s", Thread.currentThread().getName()));
            TimeUnit.MILLISECONDS.sleep(5*1000L);
            return webAsyncService.generateUUID();
        });
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("test/pool")
    public String testPoolSize(){
        for (int i = 0; i < 15; i++){
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://10.254.124.58:8080/threadPool", String.class);
//            System.out.println("***************" + responseEntity.getBody());
        }
        return "true";
    }
}
