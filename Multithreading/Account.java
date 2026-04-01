//package lessons.Multithreading;
//
//public class Account {
//    public static void main(String[] args) {
//
//    }
//    public Account transfer(Account from,Account to,  int amount) {
//
//    }
//}
//TODO
// Есть класс Account с балансом. Напишите метод transfer(Account from, Account to, int amount).
// Проблема: Если поток A переводит с акк 1 на акк 2, а поток Б одновременно переводит с акк 2 на акк 1,
//  может возникнуть взаимная блокировка (Deadlock), если использовать вложенные synchronized.
// Задание: Реализуйте безопасный перевод, используя ReentrantLock.
// Попробуйте реализовать логику так, чтобы избежать дедлока (например, блокировать аккаунты в порядке их id или хеш-кодов).
// Попробуйте использовать tryLock() для предотвращения зависания.