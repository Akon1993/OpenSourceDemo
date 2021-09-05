package com.lihg.test;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;

public class Test4SentinelByLoop {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
//            String name = IdUtil.fastSimpleUUID();
//            System.out.println(hello(name));
//            Thread.sleep(5000L);
        }
    }

    private static String hello (String name) {
        return "hello, " + name;
    }
}
