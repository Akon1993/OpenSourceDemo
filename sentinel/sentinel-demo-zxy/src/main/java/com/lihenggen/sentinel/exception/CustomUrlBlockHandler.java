package com.lihenggen.sentinel.exception;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomUrlBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "访问人数过多");
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}
