package com.lihenggen.sentinel.controller;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.zxy.common.cache.Cache;
import com.zxy.common.cache.CacheService;
import com.zxy.common.cache.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController("/cache")
public class CacheController {


    @Autowired
    private Redis redis;

    private Cache akonCache;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.akonCache = cacheService.create(CacheController.class.getName());
    }

    @GetMapping("/sadd")
    public String sadd(){
        TimeInterval timeInterval = new TimeInterval();

        String key = "zxy-sentinel-demo#com.lihenggen.sentinel.controller.CacheController#sadd";
        Set<String> collect = IntStream.range(0, 1_0000).mapToObj(i -> IdUtil.fastUUID()).collect(Collectors.toSet());
        String[] strings = collect.toArray(new String[]{});

        System.out.println("sadd " + timeInterval.interval());
        long ret = redis.sadd(key, strings);
        System.out.println("sadd " + timeInterval.interval());
        System.out.println("ret " + ret);

        Set<String> smembers = redis.smembers(key);
        System.out.println(timeInterval.interval());
        return smembers.stream().collect(Collectors.joining(","));
    }

    @GetMapping("/set")
    public void setCache(){
        akonCache.set("test#" + RandomUtil.randomNumber(), "test");
        akonCache.set("test:" + RandomUtil.randomNumber(), "test");
    }

    @GetMapping("/get")
    public String getCache(){
        // zxy-sentinel-demo#com.lihenggen.sentinel.controller.CacheController#test
        String pattern = "zxy-sentinel-demo#" + CacheController.class.getName() + "#test";
        List<String> keys = redis.keys(pattern, false, true);
        return keys.stream().collect(Collectors.joining(","));
    }

    @GetMapping("/clearByPattern")
    public void clearByPattern(){
        akonCache.clearByPattern("test#");
    }

    @GetMapping("/test")
    public void test(){
        HashMap hashMap = new HashMap();

        IntStream.range(0, 4_0000).forEach(i -> {
            hashMap.put(IdUtil.fastUUID(), true);
        });

        for (int i = 0; i < 4; i++) {
            TimeInterval timeInterval = new TimeInterval();
            System.out.println("before set " + timeInterval.interval());
            akonCache.set("2021", hashMap, 3000);
            System.out.println("after set " + timeInterval.interval());
            HashMap<String, String> map = akonCache.get("2021", HashMap::new);
            System.out.println("after get " + timeInterval.interval());
        }

    }

}
