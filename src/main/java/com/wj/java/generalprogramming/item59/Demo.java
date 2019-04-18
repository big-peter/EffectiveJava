package com.wj.java.generalprogramming.item59;

/*
    Know and use the libraries
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Demo {

    static void m1(){
        try(InputStream in = new URL("http://www.krecoder.com").openStream()){
            in.transferTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        m1();
    }

}
