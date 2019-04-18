package com.wj.java.c05generics.item28;

/*
    Prefer lists to arrays
 */

import com.wj.java.helper.Utils;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    // fails at runtime
    static void runtimeArrError(){
        Object[] objectArr = new Long[1];   // Sub[] is subtype of Super[] if Sub is subtype of Super
        objectArr[0] = "I don't fit in";    // throws ArrayStoreException
    }

    static void compileError(){
//        List<Object> objs = new ArrayList<Long>();    // compile error: incompatible type
    }

    // generic array creation is illegal, won't compile
    static void genericArray(){
//        List<String>[] stringLists = new List<String>[2]; // suppose legal
//        Object[] objects = stringLists;
//        List<Integer> intList = List.of(42);
//        objects[0] = intList; // can run: runtime type of objects is List[], runtime type of intList is List
//        long number = 10L;
//        objects[1] = number;  // not run
//        String s = stringLists[0].get(0); // class cast exception
    }

    public static void main(String[] args) throws InterruptedException {
        Utils.printSeparateLine("runtimeArrError");

        try{
            runtimeArrError();
        } catch (ArrayStoreException e){
            e.printStackTrace();
        }

        Utils.printSeparateLineAfterSleep("compileError", 500);

        compileError();

        Utils.printSeparateLine("genericArray");

        genericArray();

        Utils.printSeparateLine("chooser");

        ListBasedChooser<String> chooser = new ListBasedChooser<>(List.of("good", "bad"));
        System.out.println(chooser.choose());

    }

}
