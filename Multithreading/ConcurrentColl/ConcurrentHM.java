package lessons.ConcurrentColl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHM {

    public static void main(String[] args) throws InterruptedException {
        // 1. Создаем ConcurrentHashMap для хранения счетчиков
        Map<String, Integer> map = new ConcurrentHashMap<>();

        // 2. Задаем начальное значение счетчика
        map.put("counter", 0);

        // 3. Создаем пул из 5 потоков
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 4. Каждый поток будет 1000 раз увеличивать счетчик
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    // Увеличиваем значение по ключу "counter"
                    map.merge("counter", 1, Integer::sum);
                }
            });
        }

        // 5. Останавливаем пул потоков
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); // Ждем завершения всех потоков

        // 6. Выводим результат
        System.out.println("Финальное значение счетчика: " + map.get("counter"));
        // Должно быть 5000 (5 потоков × 1000)
    }
}

//TODO
// Создать общую карту (ConcurrentHashMap), где ключом будет строка (например, "ключ1"), а значением — число (например, счётчик).
// Запустить несколько потоков одновременно.
// Каждый поток будет много раз увеличивать значение по одному и тому же ключу (например, "ключ1") на единицу.
// После завершения всех потоков проверить, что итоговое значение равно общему числу всех увеличений (то есть данные не потерялись из-за гонки потоков).
