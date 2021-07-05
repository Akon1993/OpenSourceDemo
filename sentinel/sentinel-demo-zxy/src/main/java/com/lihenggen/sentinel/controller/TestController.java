package com.lihenggen.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/rules")
    public List<FlowRule> rules() {
        List<FlowRule> rules = FlowRuleManager.getRules();
        return rules;
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/testLongTime1")
    public String testLongTime1() {
        try {
            Thread.sleep(200L);
        } catch (Exception e) {
        }
        return "testLongTime1";
    }

    @GetMapping("/testLongTime2")
    public String testLongTime2() {
        try {
            Thread.sleep(3_000L);
        } catch (Exception e) {
        }
        return "testLongTime2";
    }

    @GetMapping("/testLongTime3")
    public String testLongTime3() {
        try {
            Thread.sleep(20_000L);
        } catch (Exception e) {
        }
        return "testLongTime3";
    }
}
