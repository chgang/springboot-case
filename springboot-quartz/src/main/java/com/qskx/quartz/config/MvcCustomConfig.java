package com.qskx.quartz.config;

import com.qskx.quartz.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author 111111
 * @date 2018-10-20 21:29
 */
@Configuration
public class MvcCustomConfig implements WebMvcConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(MvcCustomConfig.class);

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**

     * <p>
     *   视图处理器
     * </p>
     *
     * @return
     */
//    @Bean
//    public ViewResolver viewResolver() {
//        LOG.info("ViewResolver");
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册监控拦截器
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(Arrays.asList("/job/toLogin", "/job/user/login"));

    }

    /**
     * 跨域
     * @param registry
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders("*/*")
//                .allowedMethods("*")
//                .maxAge(120);
//    }

    /**
     * 资源处理器
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        LOG.info("addResourceHandlers");
//        registry.addResourceHandler("/swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

}
