package com.lihenggen.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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
            Thread.sleep(1_000L);
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
}
