package lessons.Multithreading;

public class Day2 implements Runnable {
    int count;
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
           increment();
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    public synchronized void increment() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        Day2 t1 = new Day2();
        Thread thread1 = new Thread(t1,"First thread");
        Thread thread2 = new Thread(t1,"Second thread");
        Thread thread3 = new Thread(t1,"Third thread");
        Thread thread4 = new Thread(t1,"Fourth thread");
        Thread thread5 = new Thread(t1,"Fifth thread");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Все потоки завершены.");
    }
}
//TODO Реализуйте общий счётчик, увеличиваемый 5 потоками по 1000 раз. Используйте synchronized, чтобы получить правильный результат.