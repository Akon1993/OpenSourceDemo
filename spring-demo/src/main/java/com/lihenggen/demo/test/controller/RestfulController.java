package com.lihenggen.demo.test.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import com.lihenggen.demo.test.param.SimpleParam;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
@RequestMapping("/restful")
public class RestfulController implements EnvironmentAware {

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
        System.out.println(param);
        Map<String, String[]> parameterMap = req.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            System.out.println("entry === " + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
        });
        return param;
    }

    @PostMapping("/testPost2")
    @ResponseBody
    public void m3(@RequestBody SimpleParam param, HttpServletRequest req) {

    }

    @PostMapping("/testPost3")
    @ResponseBody
    public ResponseEntity m4(@RequestBody SimpleParam param, HttpServletRequest req) {
        throw new NumberFormatException("sdfdsfsdfsdfsdf");
    }

    @PostMapping("/testPost4")
    @ResponseBody
    public void m5(@RequestBody SimpleParam param, HttpServletRequest req) {
        int i=1/0;
    }

    @PostMapping("/testPost5")
    @ResponseBody
    public ResponseEntity m6(@RequestBody SimpleParam param, HttpServletRequest req) {
        return new ResponseEntity("this is a error message...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/testPost6")
    @ResponseBody
    public ResponseEntity m7(@RequestBody SimpleParam param, HttpServletRequest req) {
        return new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT);
    }

    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @PostMapping("/upload")
    @ResponseBody
    public SimpleParam singleFileUpload(@RequestParam("file") MultipartFile file, SimpleParam param) throws IOException {
        file.transferTo(new File("D:\\temp\\test.jpg"));
        return param;
    }

    @PostMapping("/uploads")
    @ResponseBody
    public SimpleParam singleFileUpload(@RequestParam("file") MultipartFile[] files, SimpleParam param) throws IOException {
        for (MultipartFile file : files) {
            file.transferTo(new File("K:\\temp\\" + UUID.fastUUID() + ".jpg"));
        }
        return param;
    }
}
