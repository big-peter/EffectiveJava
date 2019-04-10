package com.wj.example.item2;

/*
    Consider a builder when faced with many constructor parameters
 */

class A {

    private final int p1;
    private final int p2;

    static class Builder{

        private final int p1;   // required parameter
        private int p2 = 0; //optional parameter

        Builder(int p1){
            this.p1 = p1;
        }

        Builder p2(int val){
            p2 = val;
            return this;
        }

        A build(){
            return new A(this);
        }
    }

    private A(Builder builder){
        p1 = builder.p1;
        p2 = builder.p2;
        if (p1 < 0 || p2 > 50)
            throw new IllegalArgumentException();
    }

    int getP1(){
        return p1;
    }

    int getP2(){
        return p2;
    }

}

public class BuilderDemo {

    public static void main(String[] args) {
        A a = new A.Builder(1)
                .p2(3).build();
        System.out.println(a.getP1());
    }

}
