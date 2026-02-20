

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
                if (counter % 2 == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) { }
                }
                else {
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter++;
                    lock.notify();
                }
            }
        }
    }

    public void printEven() {
        synchronized (lock) {
            while (counter <= 10) {
                if (counter % 2 != 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) { }
                }
                else {
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                    counter++;
                    lock.notify();
                }
            }
            lock.notify();
        }
    }

}
