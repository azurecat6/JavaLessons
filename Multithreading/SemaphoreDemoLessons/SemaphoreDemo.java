package Multithreading.SemaphoreDemoLessons;

import java.util.concurrent.*;

public class SemaphoreDemo {
    public static final int POOL_COUNT = 12;
    public static final int THREAD_COUNT = 5;
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(THREAD_COUNT, true);

        ExecutorService ex = Executors.newFixedThreadPool(POOL_COUNT);

        for (int i = 0; i < POOL_COUNT; i++) {
            final int Id = i;
            ex.submit(() -> {
                boolean acquired = false;
                    try {
                        System.out.println("Машина " + Id + " подъехала к парковке.");
                        if (semaphore.tryAcquire(6,TimeUnit.SECONDS)){
                            acquired = true;
                            System.out.println("Машина " + Id + " заняла место.");
                            int parkingTime = ThreadLocalRandom.current().nextInt(2000, 5001);
                            Thread.sleep(parkingTime);
                            System.out.println("Машина " + Id + " уехала.");
                        } else {
                            System.out.println("Машина " + Id + " уехала на другую стоянку.");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        if(acquired){
                            semaphore.release();
                        }

                    }
            });


        }
        ex.shutdown();
    }
}
/*
TODO:
 **Задача 1.1**
 Есть 5 парковочных мест.
 Создайте 12 машин (потоков).
 Каждая машина должна:
 - подъехать → занять место → постоять 2–5 секунд → уехать
 Если мест нет — машина ждёт сколько угодно долго.
 То же самое, но машина ждёт не больше 6 секунд.
 Если не смогла за это время припарковаться — пишет «уехала на другую стоянку» и завершается.
*/