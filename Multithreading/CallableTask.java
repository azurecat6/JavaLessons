package lessons.Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableTask {

    public static void main(String[] args) throws InterruptedException, ExecutionException{

        ExecutorService ex = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0; i < 5;i++) {
            int taskId = i;
            Callable<Integer> task = () -> {
                System.out.println("Задача #" + taskId + " работает.");
                Thread.sleep(2000);
                return ThreadLocalRandom.current().nextInt(100);
            };
            futures.add(ex.submit(task));
        }
        int sum = 0;
        for(Future<Integer> f : futures ) {
             sum += f.get();
        }
        System.out.println(sum);
        ex.shutdown();
    }
}
//TODO 🎯 Для 5-го дня тебе нужно:
// Создать 5 разных Callable<Integer> задач
// Отправить их все сразу в пул
// Собрать все результаты после отправки
// Посчитать сумму всех чисел