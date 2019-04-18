package com.wj.java.item9;

import java.io.*;

/*
    Refer try-with-resources to try-finally
 */


public class TryWithResourcesDemo {

    static String testFinallyException(String filePath) throws IOException {

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
        } finally {
            if (br != null){
                br.close();
            }
            throw new IllegalArgumentException();
        }

    }

    static String testException(String filePath) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));){
            if (filePath.equals(""))
                throw new IllegalArgumentException();
            return br.readLine();
        }
    }

    // try-with-resources with a catch clause
    static String firstLineOfFile(String filePath, String defaultValue){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));){
            return br.readLine();
        } catch (IOException e){
            System.out.println(e);
            return defaultValue;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            testFinallyException("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000); // to make console output format

        System.out.println("-------------------------------------\n");

        try {
            testException("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000);

        System.out.println("-------------------------------------\n");

        String line = firstLineOfFile("", "Hello World!");
        System.out.println(line);
    }

}
