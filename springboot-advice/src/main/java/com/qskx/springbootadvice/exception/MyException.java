package com.qskx.springbootadvice.exception;

/**
 * @author 111111
 * @date 2018-06-07 15:32
 */
public class MyException extends RuntimeException{

    public MyException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
