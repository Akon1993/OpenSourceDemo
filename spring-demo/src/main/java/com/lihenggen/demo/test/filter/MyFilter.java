package com.lihenggen.demo.test.filter;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
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
@WebFilter(filterName = "restful",urlPatterns = "/restful/*")
@Order(0)
public class MyFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 转换成包装类
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);

        String body = new String(requestWrapper.getBody(), request.getCharacterEncoding());
        log.info(requestWrapper.getRequestURI());
        log.info("Authorization === {}", requestWrapper.getHeader("Authorization"));
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        parameterMap.keySet().stream().forEach(key -> {
            log.info("entry === " + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
        });
        log.info(body);

        try {
            chain.doFilter(requestWrapper, wrapperResponse);

            log.info("状态码： ", wrapperResponse.getStatus());

            byte[] content = wrapperResponse.getContent();
            if (ArrayUtil.isNotEmpty(content)) {
                log.info("respData: {}", new String(content, "UTF-8"));
                ServletOutputStream out = response.getOutputStream();
                out.write(content);
                out.flush();
            }
        } catch (Exception e) {
            log.info("respData: {}", printStackTraceToString(e));
            throw e;
        }

    }

    @Override
    public void destroy() {

    }

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace( new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

}
