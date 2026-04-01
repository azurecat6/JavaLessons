package lessons.Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
//  AtomicInteger integer =
    int count;
    ExecutorService ex = Executors.newFixedThreadPool(100);
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        for (int i = 0; i < 100; i++) {
            counter.ex.submit(() ->{
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }

            });
        }
        counter.ex.shutdown();
        counter.ex.awaitTermination(15, TimeUnit.SECONDS);
        System.out.println(counter.count);
    }
    public synchronized void increment() {
        count++;
    }
}
//TODO
// Задача 1: Сломанный счетчик (Race Condition)
// Создайте класс Counter с одним полем int count и методом increment(), который увеличивает значение на 1.
// В методе main создайте один экземпляр Counter и запустите 100 потоков. Каждый поток должен вызвать increment() 1000 раз.
// В конце выведите значение счетчика.
// Ожидание: 100 * 1000 = 100 000.
// Задание:
// Запустите код без синхронизации. Почему результат всегда меньше ожидаемого?
//        Исправьте код, используя ключевое слово synchronized.
// Бонус: Исправьте код, используя AtomicInteger вместо synchronized.
