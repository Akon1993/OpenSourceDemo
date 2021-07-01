//package com.lihenggen.sentinel.config;
//
//import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
//import com.lihenggen.sentinel.config.InitRulers;
//import com.zxy.base.sentinel.rules.ZkDegradeDataSource;
//import com.zxy.base.sentinel.rules.ZkFlowDataSource;
//import com.zxy.base.sentinel.rules.config.ZookeeperConfig;
//import com.zxy.base.sentinel.web.CustomSentinelInterceptor;
//import com.zxy.base.sentinel.web.DashboardIntegratedConfig;
//import com.zxy.base.sentinel.web.ExcludePathPatterns;
////import com.zxy.base.sentinel.web.InitRulers;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.env.Environment;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author kedong
// */
//@Import({ZookeeperConfig.class})
//@Configuration
//@ConditionalOnWebApplication
//@ConditionalOnProperty(prefix = "csp.sentinel", value = "enable", havingValue = "true")
//public class SentinelWebConfig implements BeanFactoryAware {
//
//    private DefaultListableBeanFactory beanFactory;
//
//    public SentinelWebConfig(Environment environment) {
//        DashboardIntegratedConfig.init(environment);
//    }
//
//    @Bean
//    public CustomSentinelInterceptor sentinelWebInterceptor() {
//        return new CustomSentinelInterceptor();
//    }
//
//    @Bean
//    public SentinelResourceAspect sentinelResourceAspect() {
//        return new SentinelResourceAspect();
//    }
//
//    @Bean
//    public WebMvcConfigurer interceptorConfig() {
//        List<String> excludeList = new ArrayList<>();
//        Map<String, ExcludePathPatterns> excludePathPatterns = beanFactory.getBeansOfType(ExcludePathPatterns.class);
//        if (!excludePathPatterns.isEmpty()) {
//            excludePathPatterns.forEach((k, v) -> excludeList.addAll(Arrays.asList(v.get())));
//        }
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(sentinelWebInterceptor())
//                        .excludePathPatterns(excludeList.toArray(new String[0]))
//                        .addPathPatterns("/**");
//            }
//        };
//    }
//
//    @Bean
//    public InitRulers initRulers(Environment environment,
//                                 RequestMappingHandlerMapping handlerMapping,
//                                 ZkDegradeDataSource degradeDataSource,
//                                 ZkFlowDataSource zkFlowDataSource) {
//        return new InitRulers(environment, handlerMapping, degradeDataSource, zkFlowDataSource);
//    }
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        if (beanFactory instanceof DefaultListableBeanFactory) {
//            this.beanFactory = (DefaultListableBeanFactory) beanFactory;
//        }
//    }
//}
//
//
