package Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Numbers1 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            ex.submit(() -> {
                    if(Thread.currentThread().getName().equals("pool-1-thread-1") & finalI % 2 == 0){
                        System.out.println(Thread.currentThread().getName() + " " + finalI);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " " + finalI);
                    }
            });
        }
        ex.shutdown();
        ex.awaitTermination(1, TimeUnit.SECONDS);
    }
}
