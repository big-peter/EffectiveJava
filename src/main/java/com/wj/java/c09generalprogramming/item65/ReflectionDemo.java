package com.wj.java.c09generalprogramming.item65;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/*
    Prefer interfaces to reflection
 */

public class ReflectionDemo {
    // no compile-time type checking

    // reflective instantiation with interface access
    public static void main(String[] args) {
        String className = "java.util.HashSet";

        // translate the class name into a Class Object
        Class<? extends Set<String>> clazz = null;
        try{
            clazz = (Class<? extends Set<String>>) Class.forName(className);   // unchecked cast!
        } catch (ClassNotFoundException e) {
            fatalError(e);
        }

        // get the construct
        Constructor<? extends Set<String>> constructor = null;
        try{
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError(e);
        }

        // instantiate the set
        Set<String> s = null;
        try{
            s = constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            fatalError(e);
        }

        // use set instance
        String[] strArr = {"U", "V", "W"};
        s.addAll(Arrays.asList(strArr));
        System.out.println(s);
    }

    private static void fatalError(Exception e){
        System.out.println(e.getMessage());
        System.exit(1);
    }

}
