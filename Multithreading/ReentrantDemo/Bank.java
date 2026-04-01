package Multithreading.ReentrantDemo;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private static final ReentrantLock PRINT_LOCK = new ReentrantLock();
    List<Account> accounts = new ArrayList<Account>();

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Bank bank = new Bank();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            bank.accounts.add(new Account(random.nextInt(10000, 20001), finalI, "Acc"));
        }
        for (int i = 0; i < 30; i++) {
           new Thread(() -> {
                int size = bank.accounts.size();
                int idxFrom = random.nextInt(size);
                int idxTo = random.nextInt(size);
                while (idxTo == idxFrom) {
                    idxTo = random.nextInt(size);
                }
                Account from = bank.accounts.get(idxFrom);
                Account to   = bank.accounts.get(idxTo);

                Account first, second;
                if (from.id < to.id) {
                    first  = from;
                    second = to;
                } else {
                    first  = to;
                    second = from;
                }
                first.lock.lock();
                try {
                    second.lock.lock();
                    try {
                        int amount = random.nextInt(5000,10000);
                        int transferred = bank.transfer(from, to, amount);
                        PRINT_LOCK.lock();
                        try {
                            if (transferred == 0) {
                                System.out.printf("Недостаточно средств на счёте %d (хотели перевести %d)%n",
                                        from.id, amount);
                            } else {
                                System.out.printf("Transfer %2d → %2d : %4d   ", from.id, to.id, transferred);
                                System.out.printf("  %s %2d balance: %5d   ", from.name,from.id, from.balance);
                                System.out.printf("%s %2d balance: %5d%n", to.name,to.id, to.balance);
                            }
                        } finally {
                            PRINT_LOCK.unlock();
                        }
                    } finally {
                        second.lock.unlock();
                    }
                } finally {
                    first.lock.unlock();
                }
            }).start();
        }
    }

    public int transfer(Account from, Account to, int amount) {
        if (from.balance < amount) {
            return 0;
        }
        from.balance -= amount;
        to.balance += amount;
        return amount;
    }

    static class Account {
        final ReentrantLock lock = new ReentrantLock();
        final int id;
        int balance;
        final String name;

        Account(int balance, int id, String name) {
            this.balance = balance;
            this.id = id;
            this.name = name;
        }
    }
}



//TODO
// 1. Банк с несколькими счетами
// Есть класс BankAccount с балансом.
// Нужно реализовать переводы между счетами (transfer(from, to, amount)).
// Использовать один общий ReentrantLock для всех счетов (глобальная блокировка) и вариант с отдельным ReentrantLock на каждый счёт (fine-grained locking).
// Сравнить оба подхода по производительности при большом количестве параллельных переводов.