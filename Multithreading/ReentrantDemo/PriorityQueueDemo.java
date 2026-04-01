package Multithreading.ReentrantDemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityQueueDemo {
    final ReentrantLock lock = new ReentrantLock(true);
    static BlockingQueue<Integer> queue =  new PriorityBlockingQueue<>(20);
    Condition notEmpty = lock.newCondition();


    public static void main(String[] args) {
        PriorityQueueDemo demo = new PriorityQueueDemo();

        for (int i = 1; i <= 20; i++) {
            demo.put(i);
        }

        for (int i = 0; i < 10; i++) {   // например 5 потоков
            new Thread(() -> {
                Integer result = demo.pollWithTimeout( ThreadLocalRandom.current().nextInt(1, 8) );
                System.out.println("Получил: " + result);
            }).start();
        }
    }
    public void put(Integer item) {
        lock.lock();
        try {
            queue.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private Integer pollWithTimeout(long timeout) {
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeout * 1000L;          // если timeout был в секундах
        long remaining = timeoutMillis;

        lock.lock();
        try {
            while (queue.isEmpty() && remaining > 0) {
                try {
                    notEmpty.await(remaining, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;               // ← явно возвращаем при прерывании
                }
                remaining = timeoutMillis - (System.currentTimeMillis() - startTime);
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }
}
/*
 * TODO:  Очередь с приоритетами и таймаутом
 *  Реализовать приоритетную очередь (PriorityBlockingQueue-подобную), но с использованием ReentrantLock + Condition.
 *  Добавить метод pollWithTimeout(timeout), который ждёт не больше заданного времени.
 *  Если за timeout элемент не появился — вернуть null.
 */