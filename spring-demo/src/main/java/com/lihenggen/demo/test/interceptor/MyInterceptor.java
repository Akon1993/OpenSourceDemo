package com.lihenggen.demo.test.interceptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author lihg
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println("parameterName: " + parameterNames.nextElement());
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            System.out.println("entry === " + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
//            System.out.println("entry === " + key + " : " + parameterMap.get(key));
        });

//        System.out.println(response);
        return true;
    }
}
