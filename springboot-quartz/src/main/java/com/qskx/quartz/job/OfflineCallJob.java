package com.qskx.quartz.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OfflineCallJob implements BaseJob {
  
    private static Logger log = LoggerFactory.getLogger(OfflineCallJob.class);

    private static final String url = "";
     
    public OfflineCallJob() {
          
    }  
     
    public void execute(JobExecutionContext context)  
        throws JobExecutionException {  
        log.info("OfflineCallJob -> 执行时间: " + new Date());
        try {

            FileInputStream fis = new FileInputStream("");
//            FileInputStream fis = new FileInputStream("D:\\external-git\\springboot-case\\springboot-quartz\\userInfo.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter("");
            String line = null;
            int index = 0;
            while ((line = br.readLine()) != null){
                String [] vals = line.split("\\|");
                Map<String, Object> params = new HashMap<>();
                params.put("requestId", "offline_req_" + UUID.randomUUID().toString().replaceAll("-", ""));
                params.put("productNo", "offline_pro_" + UUID.randomUUID().toString());
                params.put("userName", vals[0]);
                params.put("identityCard", vals[1]);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();

                JSONObject tempJson = new JSONObject();
                String module = "";
                tempJson.put("params", params);
                tempJson.put("user", "");
                tempJson.put("module", JSONObject.parseArray(module));
                tempJson.put("reacquire", false);

                requestParam.add("params", JSON.toJSONString(tempJson));

                log.info("send -> 请求参数 {}", JSON.toJSONString(tempJson));
                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestParam, headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                log.info("send -> 返回结果 {}, 文件读取内容 {}", responseEntity.getBody(), vals[0] + ":" + vals[1]);

                //将request_id存到reqId.txt文件中
                pw.println(params.get("requestId").toString());

                index++;
                if (index >= 10){
                    TimeUnit.SECONDS.sleep(3);
                    index = 0;
                    log.info("读取条数 index {}", "【" + index + "】");
                }
            }
            br.close();
            isr.close();
            fis.close();
            pw.close();
        } catch (Exception e){
            log.error("execute -> 调度任务异常 error {}", e.getMessage(), e);
        }
    }

    private static final int httpConnectionTimeout = 5000;
    private static final int httpReadTimeout = 5000;

    private RestTemplate restTemplate;
    @PostConstruct
    void beanInit() {
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(httpConnectionTimeout)
                .setReadTimeout(httpReadTimeout)
                .build();
    }
}  