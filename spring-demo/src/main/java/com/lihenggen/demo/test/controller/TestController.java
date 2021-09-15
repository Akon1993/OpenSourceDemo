package com.lihenggen.demo.test.controller;

import cn.hutool.core.util.ArrayUtil;
import com.lihenggen.demo.test.param.SimpleParam;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class TestController implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @GetMapping("/testEnv")
    @ResponseBody
    public String testEnv(){
        return env.getProperty("env.test");
    }

    @PostMapping("/testPost")
    @ResponseBody
    public SimpleParam m2(@RequestBody SimpleParam param, HttpServletRequest req) {

        System.out.println("======================================================================================================");
        System.out.println(param);
        Map<String, String[]> parameterMap = req.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            System.out.println("entry === " + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
        });
        return param;
    }


    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @PostMapping("/upload")
    @ResponseBody
    public SimpleParam singleFileUpload(@RequestParam("file") MultipartFile file, SimpleParam param) throws IOException {
        file.transferTo(new File("D:\\temp\\test.jpg"));
        return param;
    }
}
