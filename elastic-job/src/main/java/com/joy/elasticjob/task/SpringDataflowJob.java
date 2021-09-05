package com.joy.elasticjob.task;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Class Description
 *
 * @author kaixuan.yin
 * @date 2021/1/4 14:24
 */
@Slf4j
@Component
public class SpringDataflowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        // 流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去；
        // 非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
        // 如果采用流式作业处理方式，建议processData处理数据后更新其状态，避免fetchData再次抓取到，从而使得作业永不停止。
        // 流式数据处理参照TbSchedule设计，适用于不间歇的数据处理。
        return Arrays.asList("1");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        log.info("待处理的数据：[{}]，[{}]", JSON.toJSONString(shardingContext), JSON.toJSONString(list));
    }
}
