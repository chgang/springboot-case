package com.qskx.servletdispatcher;

import com.qskx.servletdispatcher.servlet.CustomDispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
//@ServletComponentScan
public class SpringbootServletdispatcherApplication extends SpringBootServletInitializer {


	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CustomDispatcherServlet());
		Map<String, String> params = new HashMap<>();
//		params.put("org.atmosphere.servlet","com.qskx.servletdispatcher.servlet.CustomDispatcherServlet");
//		params.put("contextClass","org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		params.put("contextConfigLocation","application.properties");
		registration.setInitParameters(params);
		return registration;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootServletdispatcherApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServletdispatcherApplication.class, args);
	}
}
