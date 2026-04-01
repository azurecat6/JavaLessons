package Multithreading.SemaphoreDemoLessons;

import java.util.concurrent.*;

public class SemaphoreDemo3 {
    public static final int PHILO = 5;
    public static final int FORKS = 5;



    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[FORKS];
        ExecutorService ex = Executors.newFixedThreadPool(FORKS);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < FORKS; i++) {
            forks[i] = new Semaphore(1);
        }
        for (int i = 0; i < PHILO; i++) {
            final int id = i;
            Semaphore left = forks[i];
            Semaphore right = forks[(i + 1) % FORKS];
            ex.submit(() -> {
                boolean ate = false;
                try {
                    while (!ate) {
                        Thread.sleep(random.nextInt(100));
                        left.acquire();
                        if (right.tryAcquire(3, TimeUnit.SECONDS)) {
                            Thread.sleep(random.nextInt(300));
                            System.out.println("Философ " + id + " поел.");
                            ate = true;
                            left.release();
                            right.release();
                        } else {
                            left.release();
                            Thread.sleep(random.nextInt(200));
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }



        ex.shutdown();
        try {
            ex.awaitTermination(20,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ex.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Все поели.");


    }
}
//
//TODO
// **Задача 3.1**
// «Обед философов» — упрощённый вариант
// 5 философов, 5 вилок.
// Каждый философ берёт **левую** вилку, потом **правую**.
// Используйте массив семафоров (по одному на вилку).
// Добавьте условие: если философ не может взять вторую вилку в течение 3 секунд — он кладёт первую и думает ещё какое-то время.
