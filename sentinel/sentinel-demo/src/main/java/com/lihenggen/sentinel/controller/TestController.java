package com.lihenggen.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lihenggen.sentinel.anno.FlowLimit;
import com.lihenggen.sentinel.anno.TestAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@FlowLimit(flowQps = 5)
@RestController
public class TestController {


    @RequestMapping("testTT")
    @ResponseBody
    @TestAnnotation(value = "tt")
    public String testTT(){
        return "success";
    }

    @RequestMapping("testTT2")
    @ResponseBody
    @TestAnnotation(value = "tt")
    public String testTT2(){
        return "success";
    }

    @FlowLimit
    @RequestMapping ("/hello")
    public String testhello() {
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

    @FlowLimit
    @RequestMapping("/test")
    @SentinelResource("test")
    public String test() {
        return "test";
    }

    @FlowLimit
    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }

    @FlowLimit
    @RequestMapping("/test3")
    public String test3() {
        return "test3";
    }

    @FlowLimit
    @RequestMapping("/testLongTime")
    public String testLongTime() {
        try {
            Thread.sleep(5_000L);
        } catch (Exception e) {}
        return "testLongTime";
    }
}
