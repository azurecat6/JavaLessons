package lessons.Bank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//public class Bank {
//
//    public static void main(String[] args) {
//        BankAccount bankAccount = new BankAccount(1000);
//        ExecutorService ex = Executors.newFixedThreadPool(4);
//        for (int i = 0; i < 4; i++) {
//            ex.submit(() -> {
//                    try {
//                        int amount = (int)((Math.random() * 100) + 100);
//                        if(bankAccount.withDraw(amount)){
//                            Thread.sleep(200);
//                            System.out.println("Cнял : "+ amount);
//                        }
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//            });
//        }
//        ex.shutdown();
//        try{
//            if(ex.awaitTermination(10, TimeUnit.SECONDS)) {
//                System.out.println("Банкомат свободен.");
//            } else {
//                ex.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            ex.shutdownNow();
//        }
//    }
//    class BankAccount {
//        int balance;
//
//        public BankAccount(int balance) {
//            this.balance = balance;
//        }
//        public synchronized boolean withDraw(int amount) {
//            if(balance >= amount) {
//                balance -= amount;
//                return true;
//            } else {
//                System.out.println("На счету не осталось денег.");
//                return false;
//            }
//        }
//    }
//}
// TODO 🎯 Требования:
//  Общий счёт с начальной суммой (например, 1000)
//  Несколько клиентов (потоков) пытаются снять деньги
//  Синхронизация - нельзя снять больше, чем есть
//  Каждый клиент пытается снять случайную сумму
//  Выводить информацию о каждой операции
//  Главный поток ждёт завершения всех клиентов
