package com.wj.java.concurrency.item78;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
    Synchronize access to shared mutable data
 */

class BrokenStopThread {

    private boolean stopRequested;

    void test() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
            /*
                no synchronization, the code transformed to(by virtual machine)
                if (!stopRequested)
                    while(true)
                        i++;
             */
            System.out.println(String.format("BrokenStopThread background thread is terminated and value of i is %d!", i));
        });

        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;  // no guarantee the background thread will see the change of stopRequested made by main thread
    }
}

class SyncStopThread {

    private boolean stopRequested;

    synchronized boolean getStopRequested(){
        // synchronized for communication effect, that is to say making change visible to other threads.
        // synchronized prevent VM from optimizing code
        return stopRequested;
    }

    synchronized void setStopRequested(boolean stopRequested){  // both write and read method must be synchronized
        this.stopRequested = stopRequested;
    }

    void test() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!getStopRequested())
                i++;
            System.out.println(String.format("SyncStopThread background thread is terminated and value of i is %d!", i));
        });

        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        setStopRequested(true);
    }
}

class VolatileStopThread {
    private volatile boolean stopRequested;  // volatile perform no mutual exclusion, but it guarantee any thread reads the filed will see the most recently written value

    void test() throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
            System.out.println(String.format("VolatileStopThread background thread is terminated and value of i is %d!", i));
        });

        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;   // VM guarantee read and write variable is atomic unless the variable is long or double
    }
}

class ErrorVolatileCase{
    private static volatile int nextSerialNumber = 0;

    static int getNextSerialNumber(){
        return nextSerialNumber++;    // self increment is not atomic, x++ <=> y=x, x+=1
    }

    static int getValue(){
        return nextSerialNumber;
    }
}

class CorrectVolatileCase{
    private static volatile AtomicInteger nextSerialNumber = new AtomicInteger(0);

    static int getNextSerialNumber(){
        return nextSerialNumber.getAndAdd(1);    // self increment is not atomic, x++ <=> y=x, x+=1
    }

    static int getValue(){
        return nextSerialNumber.intValue();
    }
}


public class Demo {

    public static void main(String[] args) throws InterruptedException {
        BrokenStopThread brokenStopThread = new BrokenStopThread();
        brokenStopThread.test();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-------------------------------");

        SyncStopThread syncStopThread = new SyncStopThread();
        syncStopThread.test();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-------------------------------");

        VolatileStopThread volatileStopThread = new VolatileStopThread();
        volatileStopThread.test();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-------------------------------");

        int loopNum = 1000;
        int threadNum = 2;

        class SerialNumberThread extends Thread{
            @Override
            public void run(){
                for (int i=0; i<loopNum; i++){
                    ErrorVolatileCase.getNextSerialNumber();
                    CorrectVolatileCase.getNextSerialNumber();
                }
            }
        }

        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<threadNum; i++)
            threads.add(new SerialNumberThread());
        for (Thread thread: threads)
            thread.start();
        for (Thread thread: threads)
            thread.join();

        System.out.println(String.format("Error final serial number is %d not expected %d", ErrorVolatileCase.getValue(), threadNum * loopNum));

        System.out.println("-------------------------------");

        System.out.println(String.format("Correct final serial number is %d and expected %d", CorrectVolatileCase.getValue(), threadNum * loopNum));

        System.exit(1);
    }

}
