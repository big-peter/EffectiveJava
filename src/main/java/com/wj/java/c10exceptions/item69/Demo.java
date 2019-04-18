package com.wj.java.c10exceptions.item69;

/*
    Use exception only for exceptional conditions
 */

public class Demo {

    // Horrible abuse of exception. Don't ever do this!
    static void evil(){
        String[] sArr = {"asdfas", "awerewqe", "safcasd"};
        try{
            int i = 0;
            while(true){
                System.out.println(sArr[i++].toUpperCase());
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public static void main(String[] args) {
        evil();
    }

}
