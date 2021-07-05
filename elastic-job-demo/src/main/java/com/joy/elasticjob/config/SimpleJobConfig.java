package com.joy.elasticjob.config;

import cn.hutool.core.util.StrUtil;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.OdevitySortByNameJobShardingStrategy;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.joy.elasticjob.task.SpringSimpleJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Simple作业类型
 *
 * @author kaixuan.yin
 * @date 2021/1/4 12:14
 */
@Configuration
public class SimpleJobConfig {

    @Resource
    private ZookeeperRegistryCenter registryCenter;

    /***
     * 自己实现的Job
     *
     * @author kaixuan.yin
     * @date 2021/1/4
     */
    @Bean
    public SimpleJob simpleJob() {
        return new SpringSimpleJob();
    }

    /***
     * 将自己实现的job加入调度中执行
     *
     * @author kaixuan.yin
     * @date 2021/1/4
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob,
                                           @Value("${simpleJob.cron}") final String cron,
                                           @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, registryCenter, createSimpleJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    /***
     * 作业的配置
     *
     * @author kaixuan.yin
     * @date 2021/1/4
     */
    private LiteJobConfiguration createSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                              final String cron,
                                                              final int shardingTotalCount,
                                                              final String shardingItemParameters) {
        JobCoreConfiguration.Builder JobCoreConfigurationBuilder = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount);
        // 设置shardingItemParameters
        if (StrUtil.isNotEmpty(shardingItemParameters)) {
            JobCoreConfigurationBuilder.shardingItemParameters(shardingItemParameters);
        }
        // 定义作业核心配置
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfigurationBuilder.build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());
        // 作业分片策略
        // 基于平均分配算法的分片策略
        String jobShardingStrategyClass = AverageAllocationJobShardingStrategy.class.getCanonicalName();
        // 定义Lite作业根配置
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                .jobShardingStrategyClass(jobShardingStrategyClass)
                .overwrite(true)
                .build();
        return liteJobConfiguration;
    }
}
