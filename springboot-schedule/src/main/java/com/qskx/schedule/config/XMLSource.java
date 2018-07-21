package com.qskx.schedule.config;

import com.qskx.schedule.listener.MyApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author 111111
 * @date 2018-07-21 17:09
 */
@Configuration
//@ImportResource(locations = {"classpath:listener-context.xml"})
public class XMLSource {
    @Bean
    public MyApplicationListener getListener(){
        return new MyApplicationListener();
    }
}
