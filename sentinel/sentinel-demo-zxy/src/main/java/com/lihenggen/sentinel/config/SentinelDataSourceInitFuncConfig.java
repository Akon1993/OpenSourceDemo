//package com.lihenggen.sentinel.config;
//
//import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
//import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
//import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
//import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.lihenggen.sentinel.exception.CustomUrlBlockHandler;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Configuration
//@ConditionalOnProperty(prefix = "csp.sentinel", value = "enable", havingValue = "true")
//public class SentinelDataSourceInitFuncConfig implements EnvironmentAware {
//
//    private Environment environment;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    public SentinelResourceAspect sentinelResourceAspect() {
//        return new SentinelResourceAspect();
//    }
//
//    @PostConstruct
//    public void initSentinelDataSourceInitFuncConfig() {
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
//        try {
//            String x = redisFlowDataSource.readSource();
//            System.out.println(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        FlowRuleManager.register2Property(redisFlowDataSource.getProperty());
//        List<FlowRule> rules = FlowRuleManager.getRules();
//        for (FlowRule rule : rules) {
//            System.out.println(rule);
//        }
//        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
//
//    }
//
//}