package com.lihenggen.demo.test.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.lihenggen.demo.test.util.IpUtil;
import com.lihenggen.demo.test.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @author lihg
 */
@Component
@DependsOn("springContextHolder")
@Slf4j
//@Order(0)
//@WebFilter(filterName = "thirdPartRecordFilter", urlPatterns = "/restful/*")
public class ThirdPartRecordFilter extends OncePerRequestFilter {

    private static final String THIRD_PART_PATTERN = "/restful/**";

    private MultipartResolver multipartResolver = SpringContextHolder.getBean(MultipartResolver.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 只对第三方接口行拦截
        if (!new AntPathMatcher().match(THIRD_PART_PATTERN, request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }

        Long startTime = System.currentTimeMillis();
        // 转换成包装类，避免请求体和响应数据读取后数据为空的问题
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        System.out.println("======================================================================================================");
        log.info("请求ip：{}", IpUtil.getIpAddr(request));
        log.info("认证信息：{}", request.getHeader("Authorization"));
        log.info("请求路径：{}", request.getRequestURI());

        if (StrUtil.contains(request.getContentType(), ContentType.MULTIPART.getValue())) {
            // 返回 MultipartHttpServletRequest 用于获取 multipart/form-data 方式提交的请求中 上传的参数
            MultipartHttpServletRequest multipartHttpServletRequest = multipartResolver.resolveMultipart((HttpServletRequest) request);

            Map<String, String[]> parameterMap = multipartHttpServletRequest.getParameterMap();
            parameterMap.keySet().stream().forEach(key -> {
                log.info("请求参数：{}", key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
            });

            chainDoFilter(chain, startTime, responseWrapper, multipartHttpServletRequest, response);

        } else {

            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);

            Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
            parameterMap.keySet().stream().forEach(key -> {
                log.info("请求参数：{}" + key + " : " + ArrayUtil.join(parameterMap.get(key), ",", "[", "]"));
            });
            log.info(new String(requestWrapper.getBody(), request.getCharacterEncoding()));

            chainDoFilter(chain, startTime, responseWrapper, requestWrapper, response);

        }

    }

    private void chainDoFilter(FilterChain chain, Long startTime, ContentCachingResponseWrapper responseWrapper, HttpServletRequest request, HttpServletResponse originResponse) throws IOException, ServletException {
        try {
            chain.doFilter(request, responseWrapper);

            // httpStatus正常
            log.info("httpStatus：{}", responseWrapper.getStatus());
            byte[] content = responseWrapper.getContentAsByteArray();
            Long costTime = System.currentTimeMillis() - startTime;
            log.info("请求耗时：{}", costTime);
            // 记录响应具体内容
            if (ArrayUtil.isNotEmpty(content)) {
                String resultData = new String(content, responseWrapper.getCharacterEncoding());
                if (JSONUtil.isJson(resultData)) {
                    log.info("状态码：{}", StrUtil.toString(JSONUtil.getByPath(JSONUtil.parseObj(resultData), "code")));
                }
                log.info("请求结果: {}", resultData);
                // todo 持久化
                ServletOutputStream out = originResponse.getOutputStream();
                out.write(content);
                out.flush();
            }
        } catch (Exception e) {
            // httpStatus异常或代码异常，注意，经过测试这里哪怕代码异常，前端接收的状态码为500，但responseWrapper.getStatus()仍为200
            log.info("httpStatus：{}", responseWrapper.getStatus());
            log.info("异常堆栈：【{}】", ExceptionUtil.stacktraceToString(e));
            // todo 持久化
            throw e;
        }
    }

}

