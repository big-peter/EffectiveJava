package com.wj.java.c02creatinganddestroyingobjects.item7;

/*
 Eliminate Obsolete Object references
 */

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack{
    private final static int DEFAULT_INITIAL_CAPACITY = 16;
    private Object[] elements;
    private int size;

    Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    void push(Object obj){
        ensureCapacity();
        elements[size++] = obj;
    }

    Object pop(){
        if (size == 0){
            throw new EmptyStackException();
        }
        Object obj = elements[--size];
        elements[size] = null;  // Eliminate obsolete object reference
        return obj;
    }

    private void ensureCapacity(){
        if (size == elements.length){
            elements = Arrays.copyOf(elements, 2 * elements.length + 1);
        }
    }
}


public class EliminateObsoleteObjectReferenceDemo {

    public static void main(String[] args) {
        Stack stack = new Stack();
        for (int i = 0; i < 1000; i++)
            stack.push(new Object());
        for (int i = 0; i < 1000; i++)
            stack.pop();
        // stack will not maintain reference to obsolete object
    }

}
