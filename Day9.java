package lessons.Multithreading;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Day9 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Отправляем задачи
        for (int i = 0; i < 3; i++) {
            final int threadId = i; // уникальный ID для каждого потока
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 5; j++) { // фиксированное количество
                        String key = "Thread-" + threadId + "-item-" + j;
                        int value = (int)(Math.random() * 100);
                        map.put(key, value);
                        System.out.println("Добавлено: " + key + " = " + value);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Завершаем пул ПОСЛЕ отправки всех задач
        executor.shutdown();

        // Ждём завершения всех задач
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Выводим результаты
        System.out.println("\n=== Результаты ===");
        map.forEach((key, value) ->
                System.out.println(key + ": " + value));
    }
}
