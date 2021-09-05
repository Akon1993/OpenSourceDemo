package com.joy.elasticjob.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Job业务实现
 *
 * @author kaixuan.yin
 * @date 2021/1/4 12:18
 */
@Slf4j
@Component
public class SpringSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingParameter()) {
            case "A":
                log.info("本次：[{}]，[{}],[{}],[{}]", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount(), shardingContext, "000");
                break;
            case "B":
                log.info("本次：[{}]，[{}],[{}],[{}]", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount(), shardingContext, "111");
                break;
            case "C":
                log.info("本次：[{}]，[{}],[{}],[{}]", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount(), shardingContext, "222");
                break;
            default:
                log.info("本次：[{}]，[{}],[{}],[{}]", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount(), shardingContext, "默认");
                break;
        }
    }
}
