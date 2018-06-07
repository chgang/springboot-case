package com.qskx.aop.example.aspect;

import com.qskx.aop.example.annotion.UserAccess;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 111111
 * @date 2018-06-06 17:16
 */
@Aspect
@Component
@Order(1)
public class UserAccessAspect {

    @Pointcut(value = "@annotation(com.qskx.aop.example.annotion.UserAccess)")
    public void access() {

    }

    @Before("access()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("second before");
    }

    @Around("@annotation(userAccess)")
    public Object around(ProceedingJoinPoint pjp, UserAccess userAccess) {
        //获取注解里的值
        System.out.println("second around:" + userAccess.desc());
        try {
            Object o =  pjp.proceed();
            System.out.println("方法环绕proceed，结果是second :" + o);
            return o;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("access()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行 second.....");
    }

    @AfterReturning(returning = "ret", pointcut = "access()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 second : " + ret);
    }
}
