package com.wj.java.concurrency.item81;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringIntern {

    private static final Map<String, String> map = new ConcurrentHashMap<>();

    public static String intern(String s){
        String result = map.get(s);
        if (result == null){
            result = map.putIfAbsent(s, s);
            if (result == null)
                result = s;
        }

        return result;
    }

}
