package lessons.ConcurrentColl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyOnWriteCol {

    private List<Integer> list = new CopyOnWriteArrayList<>();
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteCol app = new CopyOnWriteCol();

        // Запускаем поток на запись
        app.executor.submit(app::write);

        // Запускаем поток на чтение
        app.executor.submit(app::read);

        // Завершаем работу пула
        app.executor.shutdown();
        app.executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    public void write() {
        System.out.println("Пишем...");
        for (int i = 0; i < 10; i++) {
            list.add(i);
            try {
                Thread.sleep(100); // имитируем медленную запись
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Запись завершена.");
    }

    public void read() {
        System.out.println("Читаем...");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(50); // имитируем медленное чтение
            } catch (InterruptedException ignored) {}

            // Выводим текущее состояние списка
            System.out.println("Список: " + list);
        }
        System.out.println("Чтение завершено.");
    }
}
