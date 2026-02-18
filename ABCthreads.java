package lessons.Multithreading;

import java.util.concurrent.*;

public class ABCthreads {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService ex = Executors.newFixedThreadPool(4);
        Future<String> callable = ex.submit(() -> {
            Thread.sleep(2000);
            return Thread.currentThread().getName();
        });
        for (int i = 0; i < 9; i++) {
            final int taskId = i;
            ex.submit(() -> {
                try {
                    System.out.println("Задача " + taskId + " выполняется в : " + Thread.currentThread().getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            String threadName = callable.get();
            System.out.println("Задача выполнялась в :" + threadName);
        }
        ex.shutdown();
        try{
            if(ex.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Все потоки завершены");
            } else {
                ex.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            ex.shutdownNow();
        }

    }

}

//TODO Условия:
// Создать пул потоков фиксированного размера = 4
// Отправить в пул 10 задач (Runnable)
// Каждая задача должна:
// Выводить имя потока и номер задачи
// "Работать" 1-2 секунды (Thread.sleep)
// Главный поток должен дождаться завершения всех задач
// После завершения вывести сообщение об окончании