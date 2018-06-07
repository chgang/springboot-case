package com.qskx.springbootadvice.controller;

import com.qskx.springbootadvice.exception.MyException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 111111
 * @date 2018-06-07 15:14
 */
@RestController
public class TestController {

//    @RequestMapping("/home")
//    public Object home(ModelMap modelMap) {
//        return modelMap.get("author");
//    }


    //或者 通过@ModelAttribute获取
//    @RequestMapping("/home")
//    public String home(@ModelAttribute("author") String author) {
//        return author;
//    }

    @RequestMapping("/home")
    public String home() throws Exception {

        throw new Exception("Sam 错误");
//        throw new MyException("101", "Sam 错误");

    }
}
