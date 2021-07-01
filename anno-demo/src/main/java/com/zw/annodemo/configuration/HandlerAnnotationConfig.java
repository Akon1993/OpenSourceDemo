package com.zw.annodemo.configuration;

import com.zw.annodemo.handler.HandlerTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouwen
 * @title: HandlerAnnotationConfig
 * @projectName anno-demo
 * @description:
 * @date 2021/7/110:18
 */
@Configuration
public class HandlerAnnotationConfig {

    @Bean
    public HandlerTest handlerTest(){
        return  new HandlerTest();
    }
}
