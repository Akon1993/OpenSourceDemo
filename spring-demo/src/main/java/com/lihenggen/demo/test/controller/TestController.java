package com.lihenggen.demo.test.controller;

import cn.hutool.core.util.ArrayUtil;
import com.lihenggen.demo.test.param.SimpleParam;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TestController implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @GetMapping("/testEnv")
    public String testEnv(){
        return env.getProperty("env.test");
    }

    @PostMapping("/testPost")
    public SimpleParam m2(@RequestBody SimpleParam param, HttpServletRequest req) {

        System.out.println("======================================================================================================");
        System.out.println(param);
        Map<String, String[]> parameterMap = req.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            System.out.println("entry === " + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
        });
        return param;
    }

}
