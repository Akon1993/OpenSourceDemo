package com.lihenggen.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
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

    private static final String FILE = "csp.sentinel.heartbeat.api.path";

    @PostConstruct
    public void init () {
        String path = environment.getProperty(FILE, String.class, "");
        if (!StringUtils.isEmpty(path)){
            System.setProperty(FILE, path);
        }
    }
}
