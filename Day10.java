package lessons.Multithreading;

import java.util.concurrent.CompletableFuture;

public class Day10 {
    public static void main(String[] args) {

            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep((int) (Math.random() * 1001)+ 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException();
                }
                return "Задача 1 выполнена.";
            }).exceptionally(ex ->{
                System.err.println("Произошла ошибка: " + ex.getMessage());
                return "Значение по-умолчанию.";
            });
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep((int) (Math.random() * 1001)+ 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException();
            }
            return "Задача 2 выполнена.";
        }).exceptionally(ex ->{
            System.err.println("Произошла ошибка: " + ex.getMessage());
            return "Значение по-умолчанию.";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep((int) (Math.random() * 1001)+ 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException();
            }
            return "Задача 3 выполнена.";
        }).exceptionally(ex -> {
            System.err.println("Произошла ошибка: " + ex.getMessage());
            return "Значение по-умолчанию.";
        });
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(future,future1,future2);

        allTasks.thenRun(() -> {
            System.out.println(future.join() + future1.join() + future2.join());
        }).join();

    }
}
//TODO
// Создать цепочку из трёх асинхронных задач с помощью CompletableFuture
// Условия:
// Создать 3 асинхронные задачи типа CompletableFuture<String>
// Каждая задача должна:
// Возвращать строку (например: "Задача 1 выполнена")
// "Работать" 1-2 секунды (Thread.sleep)
// Собрать результаты всех задач в одну финальную строку
// Использовать цепочку (thenApply, thenCombine, etc.)
// Вывести итоговый результат
// Бонус:
// Добавить обработку ошибок через exceptionally()
