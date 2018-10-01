package com.qskx.quartz.service.impl;

import com.qskx.quartz.service.KillProcessService;
import com.qskx.quartz.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.service.impl
 * @ClassName: KillProcessServiceImpl
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/1 19:54
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Service("killProcessService")
public class KillProcessServiceImpl implements KillProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(KillProcessServiceImpl.class);

    @Override
    public ResponseCode<String> killProcess(String programName) {

        Runtime runtime = Runtime.getRuntime();
        Set<String> pidSet = new HashSet<>();
        BufferedReader find_br = null;
        BufferedReader kill_br = null;
        try {
            Process find_process = runtime.exec("ps -ef|grep " + programName + "|grep -v grep|awk '{print $2}");
            InputStream find_is = find_process.getInputStream();
            find_br = new BufferedReader(new InputStreamReader(find_is, "UTF-8"));
            String line = null;
            while ((line = find_br.readLine()) != null){
                pidSet.add(line);
            }

            //pid不存在
            if (pidSet.size() == 0){
                LOG.info(">>>>>>>>> 该服务进程不存在,请核对服务名称. <<<<<<<<<");
                return new ResponseCode<>("服务不存在！");
            }

            //pid存在
            StringBuffer sb = new StringBuffer();
            for (String pid : pidSet) {
                try {
                    Process kill_process = Runtime.getRuntime().exec("kill -9 " + Integer.parseInt(pid));
                    InputStream kill_is = kill_process.getInputStream();
                    kill_br = new BufferedReader(new InputStreamReader(kill_is, "UTF-8"));
                    String result = null;
                    while ((result = kill_br.readLine()) != null) {
                        sb.append(result);
                    }
                } catch (Exception e) {
                    LOG.error("findPID ==> 停止进程异常 error = {}", e.getMessage(), e);
                }
            }

            return new ResponseCode<>("停止服务成功!");
        } catch (Exception e) {
            LOG.error("findPID ==> 获取 pid 异常 error = {}", e.getMessage(), e);
        } finally {
            try {
                find_br.close();
                kill_br.close();
            } catch (IOException e) {
                find_br = null;
                kill_br = null;
            }
        }
        return new ResponseCode<>("停止服务失败!");
    }


}
