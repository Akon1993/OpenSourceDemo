package com.lihg.test;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.http.HttpUtil;

import java.util.stream.IntStream;

public class Test4Sentinel {
    public static void main(String[] args) throws Exception {
        ConcurrencyTester tester = new ConcurrencyTester(1000);
        tester.test(() -> {
//            System.out.println("start...");
//            System.out.println("8081: " + HttpUtil.get("https://zxytest.zhixueyun.com/api/v1/exam/manual/test1"));
//            try {
//                Thread.sleep(20L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/test2"));
//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/test1"));
//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/testLongTime1"));
            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/testLongTime2"));

//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/testLongTime1"));

//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/test1"));
//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/testLongTime1"));
//            System.out.println("8081: " + HttpUtil.get("http://localhost:8081/testLongTime2"));
//            System.out.println("8084: " + HttpUtil.get("http://localhost:8084/test1"));
//            System.out.println(HttpUtil.get("http://localhost:8081/test2"));
//            System.out.println(HttpUtil.get("http://localhost:8081/test1"));
        });
        System.out.println(tester.getInterval());

//        while (true) {
//            System.out.println(HttpUtil.get("http://localhost:8081/testLongTime1"));
//        }


//        IntStream.range(0, 10).parallel().forEach(i -> {
//            System.out.println(i + " 8084: " + HttpUtil.get("http://localhost:8084/test1"));
//        });

    }
}
