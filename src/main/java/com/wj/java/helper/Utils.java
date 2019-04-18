package com.wj.java.helper;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Utils {

    private Utils(){}

    public static String resourcePath(String filePath) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filePath);
        if (url == null)
            throw new FileNotFoundException();
        return url.getPath();
    }

    public static void printSeparateLine(String title){
        System.out.println(String.format("---------------  %s  -----------------", title));
    }

    public static void printSeparateLineAfterSleep(String title, long sleepTime) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(sleepTime);
        printSeparateLine(title);
    }

    public static void printSeparateLineBeforeSleep(String title, long sleepTime) throws InterruptedException {
        printSeparateLine(title);
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(resourcePath("item3"));
    }
}
