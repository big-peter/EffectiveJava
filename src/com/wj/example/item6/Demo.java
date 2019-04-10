package com.wj.example.item6;

/*
    Avoid creating unnecessary object
 */

public class Demo {

    static void loopWithUnnecessary(){
        long startTime = System.currentTimeMillis();
        Long sum = 0L;  // sum in Long wrapper object
        for (int i = 0; i < Integer.MAX_VALUE; i++){
            sum += 1;   // assign operate long to Long result to Auto-Boxing and create new Object.
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    static void loop(){
        long startTime = System.currentTimeMillis();
        long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++){
            sum += 1;
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static void main(String[] args) {
        loopWithUnnecessary();
        loop();
    }

}
