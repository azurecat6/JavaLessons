package Multithreading.SemaphoreDemoLessons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


public class LibraryWithLimitedSeatsAndBooks {

    private static final int SEATS_COUNT = 5;
    private static final int BOOKS_COUNT = 10;
    private static final int READERS_COUNT = 20;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        Semaphore seats = new Semaphore(SEATS_COUNT, true);

        Semaphore[] books = new Semaphore[BOOKS_COUNT];
        for (int i = 0; i < BOOKS_COUNT; i++) {
            books[i] = new Semaphore(1, true);
        }

        Random rnd = new Random();
        ExecutorService pool = Executors.newFixedThreadPool(READERS_COUNT);

        for (int i = 1; i <= READERS_COUNT; i++) {
            final int readerId = i;
            pool.submit(() -> readerBehavior(readerId, seats, books, rnd));
            // небольшая задержка между подачей задач — чтобы логи были читаемее
            try {
                Thread.sleep(30);
            } catch (InterruptedException ignored) {
            }
        }

        pool.shutdown();
    }
    private static String currentTime() {
        return LocalTime.now().format(TIME_FORMAT);
    }

    private static void readerBehavior(int id, Semaphore seats, Semaphore[] books, Random rnd) {
        String name = "Читатель " + id;

        log(name + " подошёл к читальному залу");

        if (!tryAcquireSafe(seats, 12, TimeUnit.SECONDS, name + " не смог занять место → ушёл")) {
            return;
        }

        try {
            log(name + " занял место в зале");

            int bookIdx = rnd.nextInt(BOOKS_COUNT);
            String book = "Книга " + (bookIdx + 1);
            log(name + " выбрал книгу: " + book);

            if (!tryAcquireSafe(books[bookIdx], 5, TimeUnit.SECONDS,
                    name + " — " + book + " занята, не успел взять")) {
                return;
            }

            try {
                log("[" + currentTime() + "] " + name + " начал читать книгу " + (bookIdx + 1));

                Thread.sleep(1500 + rnd.nextInt(6000));

                log("[" + currentTime() + "] " + name + " закончил читать книгу " + (bookIdx + 1));
            } finally {
                books[bookIdx].release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log(name + " был прерван");
        } finally {
            log(name + " освободил место в зале");
            seats.release();
        }
    }

    private static boolean tryAcquireSafe(Semaphore sem, long timeoutSec, TimeUnit unit, String failMessage) {
        try {
            if (sem.tryAcquire(timeoutSec, unit)) {
                return true;
            } else {
                log(failMessage);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("Прерывание при ожидании семафора");
            return false;
        }
    }

    private static void log(String message) {
        System.out.printf("%s  %s%n", LocalTime.now().format(TIME_FORMAT), message);
    }
}
/*
TODO:
 Вы разрабатываете систему для управления доступом к библиотеке с ограниченным количеством читательских мест. В библиотеке есть:
 - **5 читательских мест** (места в читальном зале)
    - **10 книг** (каждая книга может быть прочитана только одним читателем одновременно)
    - **20 читателей** (потоков), которые хотят читать книги
    #### Требования
    1. Создайте систему, которая позволяет не более 5 читателям одновременно находиться в читальном зале
    2. Каждый читатель должен выбрать книгу и прочитать её (имитация работы)
    3. Книги могут быть прочитаны только одним читателем за раз
    4. Используйте Semaphore для управления доступом к читательским местам и книгам
    5. Реализуйте логирование действий читателей
    #### Требуемый вывод
    ```
    Читатель 1 подошел к читальному залу
    Читатель 1 занял место в зале
    Читатель 1 выбрал книгу: Книга 3
    Читатель 1 начал читать книгу 3
    Читатель 1 закончил читать книгу 3
    Читатель 1 освободил место в зале
    ...
    ```
    #### Дополнительные условия
    1. Используйте **два семафора**: один для читательских мест, другой для книг
    2. Реализуйте **tryAcquire()** с таймаутом для обработки случаев, когда ресурсы заняты
    3. Добавьте **логирование времени** начала и окончания чтения
    4. Убедитесь, что **никогда не происходит одновременного чтения одной книги несколькими читателями**
    #### Критерии оценки
    - Правильное использование Semaphore
    - Отсутствие deadlock'ов
    - Корректная работа с блокировками
    - Читаемость кода
    - Логирование действий
    - Обработка исключений
*/