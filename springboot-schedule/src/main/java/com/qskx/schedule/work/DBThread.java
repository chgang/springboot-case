package com.qskx.schedule.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 111111
 * @date 2018-07-20 17:56
 */
@Component("dBThread")
@Scope("prototype")
public class DBThread implements Runnable {

    private String msg;
    private Logger log = LoggerFactory.getLogger(DBThread.class);

//    @Autowired
//    SystemLogService systemLogService;

    @Override
    public void run() {
        //模拟在数据库插入数据
        Systemlog systemlog = new Systemlog();
        systemlog.setTime(new Date());
        systemlog.setLogdescribe(msg);
        //systemLogService.insert(systemlog);
        log.info("insert->" + msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

class Systemlog{

    private Date time;
    private String logdescribe;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLogdescribe() {
        return logdescribe;
    }

    public void setLogdescribe(String logdescribe) {
        this.logdescribe = logdescribe;
    }
}
