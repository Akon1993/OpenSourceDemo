package com.lihg.test;

import cn.hutool.http.HttpUtil;

public class Test4HttpUtil {
    public static void main(String[] args) {
        System.out.println(HttpUtil.get("http://localhost:8080/sentinel-dashboard/registry/machine"));
    }
}
