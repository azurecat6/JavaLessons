package lessons.Bank;

public class BankAccount {
    int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }
    public synchronized boolean withDraw(int amount) {
        if(balance >= amount) {
            balance -= amount;
            return true;
        } else {
            System.out.println("На счету не осталось денег.");
            return false;
        }
    }
}
