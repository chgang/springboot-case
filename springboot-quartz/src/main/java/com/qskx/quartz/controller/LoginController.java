package com.qskx.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.quartz.service.impl.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

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
//        LOG.info("userLogin ==> 用户登录信息 userName = {}, passWord = {}", userName, passWord);
        String retInfo = JSON.toJSONString(loginService.login(response, userName, passWord, true));
        LOG.info("userLogin ==> 用户登录返回 result = {}", retInfo);
        return retInfo;
    }

}
