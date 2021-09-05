package com.lihenggen.demo.test.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lihenggen.demo.test.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;
import sun.net.util.IPAddressUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;


/**
 * @author lihg
 */
//@Component
@Slf4j
@Order(0)
@WebFilter(filterName = "restful", urlPatterns = "/restful/*")
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 转换成包装类，避免请求体和响应数据读取后数据为空的问题
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        log.info("请求ip：{}", IpUtil.getIpAddr((HttpServletRequest) request));
        log.info("认证信息：{}", requestWrapper.getHeader("Authorization"));
        log.info("请求路径：{}", requestWrapper.getRequestURI());
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            log.info("请求参数：{}" + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
        });
        log.info(new String(requestWrapper.getBody(), request.getCharacterEncoding()));

        try {
            chain.doFilter(requestWrapper, responseWrapper);

            byte[] content = responseWrapper.getContentAsByteArray();
            if (ArrayUtil.isNotEmpty(content)) {
                String respData = new String(content, CharsetUtil.UTF_8);
                log.info("状态码：{}", StrUtil.toString(JSONUtil.getByPath(JSONUtil.parseObj(respData), "code")));
                log.info("响应数据: {}", respData);
                ServletOutputStream out = response.getOutputStream();
                out.write(content);
                out.flush();
            }
        } catch (Exception e) {
            log.info("异常堆栈：{}", ExceptionUtil.stacktraceToString(e));
            throw e;
        }

    }

}

