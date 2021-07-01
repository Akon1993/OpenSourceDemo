package com.lihenggen.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello() {
        Entry entry = null;
        try {
            entry = SphU.entry("demo-hello-api");
            return "ok: " + LocalDateTime.now();
        } catch (BlockException e1) {
            return "helloBlockHandler: " + LocalDateTime.now();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    @GetMapping("/test")
//    @SentinelResource("test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/testLongTime")
    public String testLongTime() {
        try {
            Thread.sleep(5_000L);
        } catch (Exception e) {}
        return "testLongTime";
    }
}
