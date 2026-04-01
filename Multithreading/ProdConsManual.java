package lessons.Multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class ProdConsManual {

    private static final int CAPACITY = 5;     // максимально 5 элементов в буфере
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Object lock = new Object();  // монитор для wait/notify

    public static void main(String[] args) throws InterruptedException {
        ProdConsManual pc = new ProdConsManual();

        Thread producer = new Thread(pc.new Producer(), "Producer");
        Thread consumer = new Thread(pc.new Consumer(), "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    /** Производитель */
    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    produce(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void produce(int value) throws InterruptedException {
            synchronized (lock) {
                // пока буфер переполнен — ждём, пока потребитель освободит место
                while (buffer.size() == CAPACITY) {
                    System.out.println(Thread.currentThread().getName()
                            + " ждёт, буфер полон");
                    lock.wait();                 // отпускаем монитор и спим
                }
                buffer.add(value);
                System.out.println(Thread.currentThread().getName()
                        + " произвёл: " + value);
                lock.notifyAll();               // пробуждаем потребителей
            }
        }
    }

    /** Потребитель */
    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                // будем считывать ровно 10 элементов (сколько их произвёл Producer)
                for (int i = 0; i < 10; i++) {
                    consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void consume() throws InterruptedException {
            synchronized (lock) {
                // пока буфер пуст — ждём, пока производитель что‑то добавит
                while (buffer.isEmpty()) {
                    System.out.println(Thread.currentThread().getName()
                            + " ждёт, буфер пуст");
                    lock.wait();
                }
                int value = buffer.remove();      // берём элемент
                System.out.println(Thread.currentThread().getName()
                        + " потребил: " + value);
                lock.notifyAll();               // пробуждаем производителей
            }
        }
    }
}
