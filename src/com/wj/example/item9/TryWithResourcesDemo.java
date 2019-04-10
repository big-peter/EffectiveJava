package com.wj.example.item9;

import java.io.*;

public class TryWithResourcesDemo {

    // try-with-resources with a catch clause
    static String firstLineOfFile(String filePath, String defaultValue){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));){
            return br.readLine();
        } catch (IOException e){
            System.out.println(e);
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        String line = firstLineOfFile("", "Hello World!");
        System.out.println(line);
    }

}
