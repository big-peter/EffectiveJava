package com.wj.java.helper;

import java.io.FileNotFoundException;
import java.net.URL;

public class Utils {

    private Utils(){}

    public static String resourcePath(String filePath) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filePath);
        if (url == null)
            throw new FileNotFoundException();
        return url.getPath();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(resourcePath("item3"));
    }
}
