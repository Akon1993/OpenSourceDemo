package com.zw.annodemo.controller;

import com.zw.annodemo.annotation.TestAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwen
 * @title: TestController
 * @projectName anno-demo
 * @description:
 * @date 2021/7/110:14
 */
@RestController
public class TestController {
    @RequestMapping("test")
    @ResponseBody
    @TestAnnotation(value = "tt")
    public String test(){
        return "success";
    }

    @RequestMapping("test2")
    @ResponseBody
    @TestAnnotation(value = "tt")
    public String test2(){
        return "success";
    }
}
