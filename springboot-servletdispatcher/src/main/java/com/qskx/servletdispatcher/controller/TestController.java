package com.qskx.servletdispatcher.controller;

import com.qskx.servletdispatcher.annotation.CustomController;
import com.qskx.servletdispatcher.annotation.CustomRequestMapping;
import com.qskx.servletdispatcher.annotation.CustomRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CustomController
@CustomRequestMapping("/test")
public class TestController {

    @CustomRequestMapping("/doTest")
    public void test1(HttpServletRequest request, HttpServletResponse response,
                      @CustomRequestParam("param") String param
                      ){
        System.out.println(param);
        try {
            response.getWriter().write( "doTest method success! param:"+param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @CustomRequestMapping("/doTest2")
    public void test2(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().write("doTest2 method success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
