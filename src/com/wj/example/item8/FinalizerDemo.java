package com.wj.example.item8;

/*
    Avoid Finalizers And Cleaners
 */


class Resource{

    private String res = "res";

    Resource(String password){
        if (!isPasswordValid(password)){
            throw new IllegalArgumentException();
        }
    }

    private boolean isPasswordValid(String password){
        return "123456".equals(password);
    }

    String getRes(){
        return res;
    }

    @Override
    public void finalize(){ // gc no guarantee to execute this method promptly or at all
        System.out.println("Resource finalizer is executed!");
        int i = 5 / 0;  // the thread will not be terminated and no message reported
        System.out.println("Resource finalizer is over!");
    }

}

class Attack extends Resource{  // attack protected res
    public static Resource res;

    Attack(){
        super("8888");
    }

    @Override
    public void finalize(){
        System.out.println("Attack finalizer is executed!");
        res = this;
        System.out.println("Attack finalizer is over!");
    }
}


public class FinalizerDemo {

    public static void main(String[] args) throws InterruptedException {
        Resource res = new Resource("123456");
        res = null;
        System.gc();

        Thread.sleep(1000);

        System.out.println("----------------------------------");

        try{
            Attack subRes = new Attack();
        }catch (Exception e){
            System.out.println(e);
        }

        System.gc();
        System.runFinalization();

        if (Attack.res != null){
            System.out.println("Resource instance has been created.");
            System.out.println("Protected res is " + Attack.res.getRes());
        }

        System.out.println("Main method is over.");
    }

}
