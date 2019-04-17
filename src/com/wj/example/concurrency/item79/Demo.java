package com.wj.example.concurrency.item79;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    Avoid excessive synchronization
 */

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        IllObservableSet<Integer> illObservableSet = new IllObservableSet<>(new HashSet<>());
        illObservableSet.addObserver((s, e) -> System.out.println(e));

        for (int i=0; i<5; i++)
            illObservableSet.add(i);

        System.out.println("-----------------------------------------");
        TimeUnit.SECONDS.sleep(1);

        illObservableSet.removeAllObserver();
        illObservableSet.clear();
        // this will not cause deadlock, because lock is hold by main thread and lock in java is reentrant, remove(call by main thread) will reacquire the lock
        // this will cause ConcurrentModificationException because it modify list when iterate the list
        illObservableSet.addObserver(new SetObserver<>() {
            public void added(ObservableSet<Integer> s, Integer e){
                System.out.println(e);

                if (e == 3){
                    s.removeObserver(this);
                }
            }
        });

        try {
            for (int i = 0; i < 5; i++)
                illObservableSet.add(i);
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("-----------------------------------------");

        class MultiThreadObserver implements SetObserver<Integer>{
            public void added(ObservableSet<Integer> s, Integer e){
                System.out.println(e);

                if (e == 3){
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try{
                        exec.submit(() -> s.removeObserver(this)).get();
                        System.out.println("Remove thread terminate!!");
                    } catch (InterruptedException | ExecutionException exception) {
                        exception.printStackTrace();
                    }finally {
                        exec.shutdown();
                    }
                }
            }
        }

        illObservableSet.removeAllObserver();
        illObservableSet.clear();
        // cause deadlock, because main thread wait remove thread terminate, but remove thread will not start forever because the lock is hold by main thread
        illObservableSet.addObserver(new MultiThreadObserver());


//        for (int i = 0; i < 5; i++)
//            illObservableSet.add(i);

        System.out.println("-----------------------------------------");

        OpenCallsObservableSet<Integer> openCallsObservableSet = new OpenCallsObservableSet<>(new HashSet<>());
        openCallsObservableSet.addObserver(new MultiThreadObserver());

        for (int i=0; i<5; i++)
            openCallsObservableSet.add(i);

        System.out.println("-----------------------------------------");

        ConcurrencyCollectionObservableSet<Integer> concurrencyCollectionObservableSet = new ConcurrencyCollectionObservableSet<>(new HashSet<>());
        concurrencyCollectionObservableSet.addObserver(new MultiThreadObserver());

        for (int i=0; i<5; i++)
            concurrencyCollectionObservableSet.add(i);

    }

}
