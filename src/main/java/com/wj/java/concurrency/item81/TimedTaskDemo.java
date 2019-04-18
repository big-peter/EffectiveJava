package com.wj.java.concurrency.item81;

import java.io.Closeable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimedTaskDemo implements Closeable {

    private ExecutorService executorService;

    public TimedTaskDemo(){
        this.executorService = Executors.newCachedThreadPool();
    }

    public long time(int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++){
            executorService.execute(() -> {
                ready.countDown();  // tell timer we're ready
                try{
                    start.await();  // wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();   // tell timer we're done
                }
            });
        }


        ready.await();  // wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown();  // tell worker to start run action
        done.await();   // wait for all workers to finish
        return System.nanoTime() - startNanos;
    }

    @Override
    public void close() {
        if (executorService != null){
            executorService.shutdown();
        }
    }
}
