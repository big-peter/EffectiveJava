package com.wj.example;

public class Demo {

    static int t(){
        try{
            try {
                return 10;
            }finally {
                return 11;
            }
        }catch (Exception e){
            System.out.println("error");
            return 2;
        }finally {
//            int i =5 / 0;
            return 3;
        }
    }

    public static void main(String[] args) {
        System.out.println(t());
    }

}
