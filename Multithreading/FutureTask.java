package lessons.Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<String>> futures = new ArrayList<>();
        ExecutorService ex = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            Callable<String> task = () -> {
                System.out.println("Скачиваю файл...");
                Thread.sleep((int) (Math.random() + 101) + 98);
                return "Файл скачан успешно!";
            };
            futures.add(ex.submit(task));
        }
        for(Future<String> s : futures) {
            System.out.println(s.get());
        }
        ex.shutdown();
        ex.awaitTermination(5,TimeUnit.SECONDS);

    }
}
//TODO
// Задача 4: Пул потоков и Future (ExecutorService)
// Представьте, что вам нужно скачать данные с 10 разных "серверов" (эмулируйте это Thread.sleep() на разное время).
// Задание:
// Создайте ExecutorService с фиксированным пулом в 3 потока.
//        Отправьте 10 задач на "скачивание". Каждая задача должна возвращать строку-результат (используйте Callable<String>).
// Соберите результаты в список, используя Future. Выведите их по мере готовности или все в конце.
// Обязательно выключите ExecutorService (shutdown).