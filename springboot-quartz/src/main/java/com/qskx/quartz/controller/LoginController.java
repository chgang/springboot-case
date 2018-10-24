package com.qskx.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.quartz.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestParam(value = "user")String userName,
                            @RequestParam(value = "pass")String passWord,
                            HttpServletResponse response){
        String retInfo = JSON.toJSONString(loginService.login(response, userName, passWord, true));
        return retInfo;
    }

}
