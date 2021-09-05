//package com.lihenggen.demo.test;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
///**
// * @PostConstruct
// * InitializingBean afterPropertiesSet
// * initMethod
// * ApplicationListener<ContextRefreshedEvent> onApplicationEvent // 这个事件是spring的
// * CommandLineRunner run
// * ApplicationListener<ApplicationReadyEvent> onApplicationEvent // 这个事件是springboot的
// * @PreDestroy
// * DisposableBean destroy
// * destroyMethod
// */
//public class LifeCycle implements InitializingBean, DisposableBean, CommandLineRunner, ApplicationListener<ContextRefreshedEvent> {
//
//    @PostConstruct
//    private void postConstruct() {
//        System.out.println("@PostConstruct");
//    }
//
//    @PreDestroy
//    private void preDestroy() {
//        System.out.println("@PreDestroy");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("InitializingBean afterPropertiesSet");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("DisposableBean destroy");
//    }
//
//    private void initMethod() {
//        System.out.println("initMethod");
//    }
//
//    private void destroyMethod() {
//        System.out.println("destroyMethod");
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("CommandLineRunner run");
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        System.out.println("ApplicationListener<ContextRefreshedEvent> onApplicationEvent");
//    }
//}
