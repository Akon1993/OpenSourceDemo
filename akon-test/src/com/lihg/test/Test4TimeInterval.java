package com.lihg.test;

import cn.hutool.core.date.TimeInterval;

public class Test4TimeInterval {
    public static void main(String[] args) throws InterruptedException {
        TimeInterval timeInterval = new TimeInterval();
        Thread.sleep(1000L);
        System.out.println(timeInterval.interval());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000L);
        System.out.println(timeInterval.intervalRestart());



    }
}
