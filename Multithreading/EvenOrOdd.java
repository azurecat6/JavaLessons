

public class EvenOrOdd {
    int counter = 1;
    Object lock = new Object();

    public static void main(String[] args) {
        EvenOrOdd eoo = new EvenOrOdd();

        Thread t1 = new Thread(() -> {
            eoo.printOdd();
        });

        Thread t2 = new Thread(() -> {
            eoo.printEven();
        });

        t1.start();
        t2.start();
    }

    public void printOdd() {
        synchronized (lock) {
            while (counter <= 10) {
                // Если сейчас четное (не мой ход) -> ждем
                if (counter % 2 == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) { }
                }
                else {
                    // Мой ход (нечетное)
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter++;
                    // Будим четный поток
                    lock.notify();
                }
            }
        }
    }

    public void printEven() {
        synchronized (lock) {
            while (counter <= 10) {
                // Если сейчас нечетное (не мой ход) -> ждем
                if (counter % 2 != 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) { }
                }
                else {
                    // Мой ход (четное)
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter++;
                    // Будим нечетный поток
                    lock.notify();
                }
            }
            lock.notify();
        }
    }
}