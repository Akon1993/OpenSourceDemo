package com.joy.elasticjob.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zk注册中心
 *
 * @author kaixuan.yin
 * @date 2021/1/4 12:09
 */
@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class ZkRegistryCenterConfig {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${regCenter.serverList}") final String serverList, @Value("${regCenter.namespace}") final String namespace) {
        // serverList 连接Zookeeper服务器的列表，包括IP地址和端口号，多个用逗号隔开
        // namespace 命名空间
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(serverList, namespace);
        // 等待重试的间隔时间的初始值单位，单位毫秒，默认1000
        zkConfig.setBaseSleepTimeMilliseconds(1000);
        // 等待重试的间隔时间的最大值单位，单位毫秒，默认3000
        zkConfig.setMaxSleepTimeMilliseconds(3000);
        // 最大重试次数，默认值3
        zkConfig.setMaxRetries(3);
        // 会话超时时间，单位毫秒，默认60000
        zkConfig.setSessionTimeoutMilliseconds(60000);
        // 连接超时时间，单位毫秒，默认15000
        zkConfig.setConnectionTimeoutMilliseconds(15000);
        return new ZookeeperRegistryCenter(zkConfig);
    }
}
