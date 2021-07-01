//package com.lihenggen.sentinel.config;
//
//import org.springframework.core.env.Environment;
//import org.springframework.util.StringUtils;
//
///**
// * 接入dashboard相关配置
// *
// * @author kedong
// */
//public final class DashboardIntegratedConfig {
//
//    public static void init(Environment environment) {
//        String appName = environment.getProperty("spring.application.name", String.class, "");
//        String active = environment.getProperty("spring.profiles.active", String.class, "");
//        if (!StringUtils.isEmpty(active)) {
//            active += "-";
//        }
//        appName = active + appName;
//        if (!StringUtils.isEmpty(appName)) {
//            System.setProperty("project.name", appName);
//        }
//
//        String server = environment.getProperty("csp.sentinel.dashboard.server", String.class, "");
//        String port = environment.getProperty("csp.sentinel.api.port", String.class, "");
//        String ip = environment.getProperty("csp.sentinel.heartbeat.client.ip", String.class, "");
//
//        System.setProperty("csp.sentinel.dashboard.server", server);
//        System.setProperty("csp.sentinel.heartbeat.client.ip", ip);
//        if (!StringUtils.isEmpty(port)) {
//            System.setProperty("csp.sentinel.api.port", port);
//        }
//    }
//}
