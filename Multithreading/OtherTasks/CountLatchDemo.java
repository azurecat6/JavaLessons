package Multithreading.OtherTasks;

import java.util.concurrent.CountDownLatch;

public class CountLatchDemo {
    CountDownLatch mainLatch = new CountDownLatch(1);
    CountDownLatch subLatch = new CountDownLatch(3);


    public static void main(String[] args) {

    }
}
/*
 *TODO: Задача 1:
 * Создайте программу, в которой главный поток ожидает завершения трех рабочих потоков.
 * Каждый рабочий поток выполняет свою задачу (например, выводит сообщение), а затем сигнализирует о завершении через CountDownLatch.
 * Главный поток должен дождаться окончания всех потоков и только тогда продолжить выполнение.

 */
