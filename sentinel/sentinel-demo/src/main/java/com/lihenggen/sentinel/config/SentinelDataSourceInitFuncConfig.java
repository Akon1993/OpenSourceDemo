package com.lihenggen.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebTotalInterceptor;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.lihenggen.sentinel.anno.AnnotationScan;
import com.lihenggen.sentinel.anno.FlowLimitAnnotationHandler;
import com.lihenggen.sentinel.handler.HandlerTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@Configuration
@ConditionalOnProperty(prefix = "csp.sentinel", value = "enable", havingValue = "true")
public class SentinelDataSourceInitFuncConfig implements BeanFactoryAware, EnvironmentAware, ApplicationContextAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public SentinelWebTotalInterceptor sentinelWebTotalInterceptor() {
        return new SentinelWebTotalInterceptor();
    }

    @Bean
    public SentinelWebInterceptor SentinelWebInterceptor() {
        return new SentinelWebInterceptor();
    }

    @Bean
    public FlowLimitAnnotationHandler flowLimitAnnotationHandler() {
        return new FlowLimitAnnotationHandler(applicationContext);
    }

    @Bean
    public HandlerTest handlerTest() {
        return new HandlerTest();
    }

    @Bean
    public AnnotationScan annotationScan() {
        return new AnnotationScan();
    }

    @Bean
    public WebMvcConfigurer interceptorConfig(FlowLimitAnnotationHandler flowLimitAnnotationHandler) throws ExecutionException, InterruptedException {
        Set<String> allSentinelResource = flowLimitAnnotationHandler.getAllSentinelResource();
        System.out.println(allSentinelResource);
        
        
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(SentinelWebInterceptor())
                        .addPathPatterns("/**");
            }
        };
    }

//    @PostConstruct
//    public void initSentinelDataSourceInitFuncConfig(FlowLimitAnnotationHandler flowLimitAnnotationHandler) {
//        Set<String> allSentinelResource = flowLimitAnnotationHandler.getAllSentinelResource();
//        System.out.println(allSentinelResource);
//
//        DashboardIntegratedConfig.init(environment);
//        String appName = System.getProperty("project.name");
//
//        final String remoteAddress = "ubuntu.wsl:2181";
//
//        // 流控规则
//        final String flowPath = "/sentinel_rule_config/" + appName + "/flow";
//        System.out.println("flowPath:" + flowPath);
//        ReadableDataSource<String, List<FlowRule>> redisFlowDataSource = new ZookeeperDataSource<>(remoteAddress, flowPath,
//                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
//                }));
//        FlowRuleManager.register2Property(redisFlowDataSource.getProperty());
//        List<FlowRule> rules = FlowRuleManager.getRules();
//        for (FlowRule rule : rules) {
//            System.out.println(rule);
//        }
//
//    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        }
    }
}