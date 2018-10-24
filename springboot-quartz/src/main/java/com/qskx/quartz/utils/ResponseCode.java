package com.qskx.quartz.utils;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.utils
 * @ClassName: ResponseCode
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/1 20:16
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public class ResponseCode<T> {

    private int code;
    private T content;
    private String msg;

    public ResponseCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseCode(T content){
        this.code = 0;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
