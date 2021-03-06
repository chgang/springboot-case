package com.qskx.springbootadvice.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.springbootadvice.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 111111
 * @date 2018-06-07 15:22
 */
@ControllerAdvice
@Component
public class MyControllerAdvice implements ResponseBodyAdvice{

    private static final Logger log = LoggerFactory.getLogger(MyControllerAdvice.class);

    private ThreadLocal<Object> modelHolder = new ThreadLocal<>();
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Object object = binder.getTarget();
        modelHolder.set(binder.getTarget());
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Magical Sam");
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", ex.getMessage());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(value = MyException.class)
//    public Map myErrorHandler(MyException ex) {
//        Map map = new HashMap();
//        map.put("code", ex.getCode());
//        map.put("msg", ex.getMsg());
//        return map;
//    }

    @ExceptionHandler(value = MyException.class)
    public ModelAndView myErrorHandler(MyException ex, HttpServletRequest request) {
        log.info("uri={} | requestBody={}", request.getRequestURI(),
                JSON.toJSONString(modelHolder.get()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("code", ex.getCode());
        modelAndView.addObject("msg", ex.getMsg());
        return modelAndView;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Object value = modelHolder.get();
        return o;
    }
}
