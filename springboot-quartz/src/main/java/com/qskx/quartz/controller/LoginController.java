package com.qskx.quartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 111111
 * @date 2018-09-15 21:39
 */
@Controller
@RequestMapping("/job")
public class LoginController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

}
