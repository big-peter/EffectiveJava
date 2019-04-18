package com.wj.java.concurrency.item81;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
    Prefer concurrency utilities to wait and notify
 */

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        String s1 = new String("123");
        String s2 = new String("123");
        System.out.println(s1 == s2);
        System.out.println(s1 == StringIntern.intern(s1));
        System.out.println(StringIntern.intern(s1) == StringIntern.intern(s2));

        System.out.println("--------------------------");

        try(TimedTaskDemo timedTaskDemo = new TimedTaskDemo();){
            long time = timedTaskDemo.time(3, () -> {
                try {
                    System.out.println("action start");
                    TimeUnit.NANOSECONDS.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    System.out.println("action finish");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            });
            System.out.println(time);
        }

    }

}
