package com.wj.example;

import java.lang.reflect.Array;
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        System.out.println(map.putIfAbsent("2", 2));
        System.out.println(map.putIfAbsent("1", 3));
        System.out.println(map);

        String s = "123".intern();
        System.out.println(s == "123");
        System.out.println(s == new String("123"));
        System.out.println("123" == new String("123"));
        System.out.println("123" == new String("123").intern());

    }
}
