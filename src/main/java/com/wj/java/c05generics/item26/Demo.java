package com.wj.java.c05generics.item26;

/*
    Don'subtype1 use raw types
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Demo {

    static class A{
        @Override
        public String toString(){
            return "A instance";
        }
    }
    static class B{}

    // raw type case
    static void rawType(){
        Collection as = new ArrayList();
        as.add(new A());    // emits "unchecked call" warning
        as.add(new A());
        as.add(new B());    // compile and runs without error

        for(Object o : as){
            A a = (A)o; // runtime ClassCastException
            System.out.println(a);
        }
    }

    // parameterized type case
    static void parameterizedType(){
        Collection<A> as = new ArrayList<>();
        as.add(new A());
        as.add(new A());
//        as.add(new B());    // generate compile-time error: incompatible types

        for(A a : as){
            System.out.println(a);
        }
    }

    static void subtype1(){
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, 42);
        String s = strings.get(0);  // has compiler-generated cast
    }

    // list is raw type, parameterized type List<String> is subtype1 of raw type List for backward compatible
    private static void unsafeAdd(List list, Object o){
        list.add(o);    // emit compile-time warning
    }

    static void subtype2(){
        List<String> strings = new ArrayList<>();
//        unsafeAdd2(strings, 42);  // compile-time error. List<String> is not subtype of List<Object>
//        String s = strings.get(0);
    }

//    https://stackoverflow.com/questions/1998544/method-has-the-same-erasure-as-another-method-in-type
//    a raw type was "override-equivalent" to any generified type
//    private static void unsafeAdd(List<Object> list, Object o){
//            list.add(o);
//    }

    private static void unsafeAdd2(List<Object> list, Object o){
        list.add(o);    // emit compile-time warning
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            rawType();
        } catch (ClassCastException e){
            TimeUnit.MILLISECONDS.sleep(200);
            e.printStackTrace();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-------------------");

        parameterizedType();


        System.out.println("-------------------");
        TimeUnit.SECONDS.sleep(1);

        try {
            subtype1();
        } catch (ClassCastException e){
            e.printStackTrace();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-------------------");

        subtype2();
    }

}
