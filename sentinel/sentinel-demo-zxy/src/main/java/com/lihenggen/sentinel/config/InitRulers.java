package com.lihenggen.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.zxy.base.sentinel.rules.ZkDegradeDataSource;
import com.zxy.base.sentinel.rules.ZkFlowDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 添加默认规则
 *
 * @author kedong
 */
public class InitRulers implements CommandLineRunner {
    private final RequestMappingHandlerMapping handlerMapping;

    private final List<String> EXCLUDE_URL = Arrays.asList("/error", "/");
    private final Double qps;
    private final Double rt;
    private final int timeWindow;
    private final ZkFlowDataSource flowDataSource;
    private final ZkDegradeDataSource degradeDataSource;

    public InitRulers(Environment environment, RequestMappingHandlerMapping handlerMapping, ZkDegradeDataSource degradeDataSource,
                      ZkFlowDataSource zkFlowDataSource) {
        this.handlerMapping = handlerMapping;
        this.degradeDataSource = degradeDataSource;
        this.flowDataSource = zkFlowDataSource;

        this.qps = environment.getProperty("csp.sentinel.flow.qps", Double.class, -1D);
        this.rt = environment.getProperty("csp.sentinel.degrade.rt", Double.class, -1D);
        this.timeWindow = environment.getProperty("csp.sentinel.degrade.timeWindow", Integer.class, 60);
    }

    /**
     * 流控规则：
     * 限流类型： QPS
     * 流控模式： 直接
     * 流控效果： 快速失败
     */
    private FlowRule buildFlowRule(String resourceName) {
        if (qps < 0) {
            return null;
        }
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setCount(qps);
        return rule;
    }

    /**
     * 降级策略默认为 平均响应时间
     * 时间窗口默认为 60s
     */
    private DegradeRule buildDegradeRule(String resourceName) {
        if (rt <= 0) {
            return null;
        }
        DegradeRule rule = new DegradeRule();
        rule.setResource(resourceName);
        // 设置平均响应时间（毫秒）
        rule.setCount(rt);
        // 设置时间窗口
        rule.setTimeWindow(timeWindow);
        return rule;
    }


    @Override
    public void run(String... args) throws Exception {
        // 读取zk里面的规则配置
        List<FlowRule> flowRuleList = flowDataSource.read();
        List<DegradeRule> degradeRuleList = degradeDataSource.read();

        if (qps < 0 && rt < 0) {
            return;
        }

        List<String> resources = new ArrayList<>();
//        setResources(resources);
        Set<String> flowRuleSet = Optional.ofNullable(flowRuleList)
                .map(list -> list.stream().map(FlowRule::getResource).collect(Collectors.toSet()))
                .orElse(new HashSet<>());


        Set<String> degradeRuleSet = Optional.ofNullable(degradeRuleList)
                .map(list -> list.stream().map(DegradeRule::getResource).collect(Collectors.toSet()))
                .orElse(new HashSet<>());


        FlowRule flowRule;
        DegradeRule degradeRule;
        List<FlowRule> flowRules = new ArrayList<>();
        List<DegradeRule> degradeRules = new ArrayList<>();
        // 找出没有设置过规则的接口地址
        for (String url : resources) {
            if (flowRuleSet.isEmpty() || !flowRuleSet.contains(url)) {
                flowRule = buildFlowRule(url);
                if (flowRule != null) {
                    flowRules.add(flowRule);
                }
            }
            if (degradeRuleSet.isEmpty() || !degradeRuleSet.contains(url)) {
                degradeRule = buildDegradeRule(url);
                if (degradeRule != null) {
                    degradeRules.add(degradeRule);
                }
            }
        }

        /**
         * 将这些默认规则刷到zk中
         */
        flowDataSource.write(flowRules);
        degradeDataSource.write(degradeRules);
    }

    private void setResources(List<String> resources) {
        Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            Set<String> patterns = entry.getKey().getPatternsCondition().getPatterns();
            for (String url : patterns) {
                if (EXCLUDE_URL.contains(url)) {
                    continue;
                }
                resources.add(url);
            }
        }
    }
}
