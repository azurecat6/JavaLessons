package lessons.Multithreading;

import java.util.concurrent.*;

public class Day6 {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        ExecutorService ex = Executors.newFixedThreadPool(2);

        ex.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put(i);
                    System.out.println("Произведено: " + i);
                    Thread.sleep((int)(Math.random() * 401) + 100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        ex.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Integer it = queue.take();
                    System.out.println("Получено: " + it);
                    Thread.sleep((int)(Math.random() * 101) + 200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        ex.shutdown();
        ex.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Работа завершена.");
    }
}
//TODO
// Реализовать паттерн "производитель-потребитель" с использованием ArrayBlockingQueue
// Условия:
// Создать очередь размером 5 элементов
// Производитель:
// Добавляет числа от 1 до 10 в очередь
// Между добавлениями ждать 100-500ms
// Потребитель:
// Извлекает числа из очереди и выводит их
// Между извлечениями ждать 200-300ms
// Использовать 2 потока - один производитель, один потребитель
// Использовать ArrayBlockingQueue
// Главный поток должен дождаться завершения обоих потоков