package com.zw.annodemo.handler;

import com.zw.annodemo.annotation.TestAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhouwen
 * @title: HandlerTest
 * @projectName anno-demo
 * @description:
 * @date 2021/7/110:19
 */
public class HandlerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void  init(){
        String[] bean = applicationContext.getBeanNamesForAnnotation(Controller.class);
        String[] restBean = applicationContext.getBeanNamesForAnnotation(RestController.class);
        List<String> beanList = ArrayUtils.toUnmodifiableList(bean);
        List<String> restBeanList = ArrayUtils.toUnmodifiableList(restBean);
        Set<String> set=new HashSet<String>();
        set.addAll(beanList);
        set.addAll(restBeanList);
        for (String beanName : set) {
            Object obj = applicationContext.getBean(beanName);
            Method[] declaredMethods = obj.getClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.isAnnotationPresent(TestAnnotation.class)) {
                    System.out.println(declaredMethod.getName() + ": " + declaredMethod.getAnnotation(TestAnnotation.class).value());
                }
            }
        }

    }
}
