package com.qskx.web.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 111111
 * @date 2018-08-18 23:13
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
