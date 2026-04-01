package Multithreading.SemaphoreDemoLessons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo2 {
    public static final int PRINTER_COUNT = 1;
    public static final int SCANNER_COUNT = 1;
    public static final int PC_COUNT = 2;
    public static final int STUDENTS = 10;

    public static void main(String[] args) {
        Semaphore printersph = new Semaphore(PRINTER_COUNT, true);
        Semaphore scannersph = new Semaphore(SCANNER_COUNT, true);
        Semaphore pcsph = new Semaphore(PC_COUNT, true);

        ExecutorService ex = Executors.newFixedThreadPool(STUDENTS);

        for (int i = 0; i < STUDENTS; i++) {
            final int Id = i;
            ex.submit(() -> {
                boolean printer = false;
                boolean scanner = false;
                boolean pc = false;
            try {
                printersph.acquire();
                printer = true;
                System.out.println("Студент " + Id + " занял принтер.");

                scannersph.acquire();
                scanner = true;
                System.out.println("Студент " + Id + " занял сканер.");

                pcsph.acquire();
                pc = true;
                System.out.println("Студент " + Id + " занял компьютер.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (pc) pcsph.release();
                if (scanner) scannersph.release();
                if (printer) printersph.release();
            }
            });
        }
        ex.shutdown();
    }
}
/*
TODO:
 Задача 2.1**
 В комнате 3 ресурса разных типов:
 - принтер (1 шт)
 - сканер (1 шт)
 - компьютер (2 шт)
 Создайте 10 потоков-студентов.
 Каждый студент хочет:
 1. взять принтер
 2. взять сканер
 3. взять компьютер
 (в любом порядке)
 После использования — все три ресурса освобождает.
 Реализуйте с помощью трёх семафоров.
*/