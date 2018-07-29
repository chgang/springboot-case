package com.qskx.aop.example.controller;

import com.qskx.aop.example.annotion.UserAccess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 111111
 * @date 2018-06-06 15:56
 */
@RestController
public class FirstController {

    @RequestMapping("/first")
    public Object first() {
        return "first controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        try {
            return 1 / 0;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @RequestMapping("/second")
    @UserAccess(desc = "second")
    public Object second() {
        return "second controller";
    }
}
