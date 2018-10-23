package com.qskx.quartz.interceptor;

import com.qskx.quartz.annotion.PermissionLimit;
import com.qskx.quartz.entity.SheduleUser;
import com.qskx.quartz.service.impl.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 111111
 * @date 2018-10-20 21:16
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("handler className = {}", handler.getClass().getName());
        if (!(handler instanceof HandlerMethod)){
            return super.preHandle(request, response, handler);
        }

        boolean needLogin = true;
        boolean needAdmin = true;
        HandlerMethod methodHandle = (HandlerMethod)handler;
        PermissionLimit permission = methodHandle.getMethodAnnotation(PermissionLimit.class);
        if (permission != null){
            needLogin = permission.limit();
            needAdmin = permission.adminUser();
        }

        if (needLogin){
            SheduleUser sheduleUser = loginService.ifLogin(request);
            if (sheduleUser == null){
                response.sendRedirect(request.getContextPath() + "/job/toLogin");
                return false;
            }

            if (needAdmin && sheduleUser.getPermission() != 1){
                throw new RuntimeException("权限拦截");
            }

            request.setAttribute(LoginService.LOGIN_IDENTITY, sheduleUser);
        }
        return super.preHandle(request, response, handler);
    }

}
