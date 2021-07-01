package com.lihenggen.sentinel.anno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnnotationScan implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(AnnotationScan.class);

    /**
     * 初始化方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        ApplicationContext applicationContext = event.getApplicationContext();
//        String[] bean = applicationContext.getBeanNamesForAnnotation(Controller.class);
//        String[] restBean = applicationContext.getBeanNamesForAnnotation(RestController.class);
//        List<String> beanList = ArrayUtils.toUnmodifiableList(bean);
//        List<String> restBeanList = ArrayUtils.toUnmodifiableList(restBean);
//        Set<String> set = new HashSet<String>();
//        set.addAll(beanList);
//        set.addAll(restBeanList);
//        for (String beanName : set) {
//            Object obj = applicationContext.getBean(beanName);
//            Method[] declaredMethods = obj.getClass().getDeclaredMethods();
//            for (Method declaredMethod : declaredMethods) {
//                System.out.println("========================");
//                System.out.println(declaredMethod.isAnnotationPresent(FlowLimit.class));
//            }
//        }
    }
}