package Multithreading.SemaphoreDemoLessons;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Counter {
    private int  count = 0;

    public static void main(String[] args) {
        Counter counter = new Counter();
        ExecutorService ex = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10 ; i++) {
            ex.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });

        }

        ex.shutdown();
        try {
            ex.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ex.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println(counter.get());
    }

    synchronized void increment() {
        count++;
    }
    public synchronized int get() {
        return count;
     }
}
