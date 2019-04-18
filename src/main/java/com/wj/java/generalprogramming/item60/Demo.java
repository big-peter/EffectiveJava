package com.wj.java.generalprogramming.item60;

/*
    Avoid float and double if exact answers are required
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Demo {

    static void m1(){
        // 如果某个十进制数的最简分数的分母包含非2因子，则它不能被二进制浮点数表示
        // 十进制可以表示任意一个数， 二进制不能
        System.out.println(1.03 - 0.42);
        System.out.println(1 - 9 * 0.1);
    }

    static void m2(){
        System.out.println(BigDecimal.valueOf(1.03).subtract(BigDecimal.valueOf(0.42)));

        System.out.println(BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(9.0))));

        BigDecimal pi = BigDecimal.valueOf(3.14159);
        System.out.println(pi.remainder(BigDecimal.valueOf(2.0)));
        System.out.println(pi.round(new MathContext(2, RoundingMode.HALF_UP)));
        System.out.println(pi.setScale(2, RoundingMode.HALF_UP));
    }

    public static void main(String[] args) {
        m1();

        System.out.println("---------------------------");

        m2();
    }

}
