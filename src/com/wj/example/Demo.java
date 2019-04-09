package com.wj.example;

public class Demo {

    public static void main(String[] args) {
        String x = "123";
        String y = "123";
        System.out.println(x.hashCode());
        System.out.println(y.hashCode());
        System.out.println(x == y);
    }
}
