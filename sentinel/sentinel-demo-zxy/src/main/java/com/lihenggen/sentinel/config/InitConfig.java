package com.lihenggen.sentinel.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Configuration
public class InitConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 公司封装的sentinel未将此参数初始化进系统参数
     */
    private static final String FILE = "csp.sentinel.heartbeat.api.path";

    /**
     * 日志文件名中是否加入进程号，用于单机部署多个应用的情况
     */
    private static final String MULTIPLE_APP_IN_ONE_MACHINE = "csp.sentinel.log.use.pid";

    /**
     * 最大的有效响应时长（ms），超出此值则按照此值记录
     */
    private static final String STATISTIC_MAX_RT = "csp.sentinel.statistic.max.rt";

    @PostConstruct
    public void init() {
        String path = environment.getProperty(FILE, String.class, "");
        if (!StringUtils.isEmpty(path)) {
            System.setProperty(FILE, path);
        }

        String isMulti = environment.getProperty(MULTIPLE_APP_IN_ONE_MACHINE, String.class, "");
        if (!StringUtils.isEmpty(isMulti)) {
            System.setProperty(MULTIPLE_APP_IN_ONE_MACHINE, isMulti);
        }

        String maxRt = environment.getProperty(STATISTIC_MAX_RT, String.class, "");
        if (!StringUtils.isEmpty(maxRt)) {
            System.setProperty(STATISTIC_MAX_RT, maxRt);
        }
    }
}
