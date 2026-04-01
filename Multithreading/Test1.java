package Multithreading;

import java.util.concurrent.*;

public class Test1 {

    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(2);

        Callable<Integer> cal1 = () -> {
            Thread.sleep(2000);
            return 10;
        };
        Callable<Integer> cal2 = () -> {
            Thread.sleep(1000);
            return 20;
        };
        Future<Integer> future1 = ex.submit(cal2);
        Callable<Integer> cal3 = () -> {
            Integer fut = future1.get(3, TimeUnit.SECONDS);
            return fut + 10;
        };



        ex.submit(cal1);
        ex.submit(cal3);
        Future<Integer> future2 = ex.submit(cal1);
        Future<Integer> future3 = ex.submit(cal3);

        ex.shutdown();
        try {
            System.out.println(future2.get()+ future3.get() + future1.get());
            ex.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException b) {
            ex.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
