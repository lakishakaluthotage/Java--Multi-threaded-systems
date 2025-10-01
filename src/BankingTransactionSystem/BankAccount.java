//UoW No- W1867117

package BankingTransactionSystem;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class BankAccount {
    // Declared Instance Variables
    private final int id; //Unique identifier is used for identify each bank account
    private double balance; //Current balance of the bank account
    private final ReentrantReadWriteLock lock; //Reentrantreadwritelock is used for controlling the concurrent access

    //Parameterized constructor initialize the account with the id and the initial balance
    public BankAccount(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;//Set the initial balance
        // Fair lock ensures first-come-first-served
        this.lock = new ReentrantReadWriteLock(true); //Initializing the lock
    }

    // Getter is used to return the account ID
    public int getId() {
        return id;
    }

    // Method getBalance safely retrieve the current balance concurrently
    public double getBalance() {
        lock.readLock().lock(); //ReadLock is acquired to allow concurrent reads
        try {
            return balance; //Return the current balance
        } finally {
            lock.readLock().unlock(); // readLock is released after reading the balance
        }
    }

    // Method deposit safely deposit money into the account
    public void deposit(double amount) {
        lock.writeLock().lock(); // writeLock is acquired
        try {
            balance += amount; // Deposited amount is added to the balance
            System.out.println("Deposited $" + amount + " into Account " + id + ". New Balance: $" + balance);
        } finally {
            lock.writeLock().unlock(); //writeLock is released after the operation
        }
    }

    // Method withdraw safely withdraw money from the account
    public void withdraw(double amount) {
        lock.writeLock().lock(); // writeLock is acquired
        try {
            // Check whether there is sufficient funds
            if (balance >= amount) {
                balance -= amount; // Deduct the withdrawal amount from the balance
                System.out.println("Withdrew $" + amount + " from Account " + id + ". New Balance: $" + balance);
            } else {
                System.out.println("Insufficient funds in Account " + id);
            }
        } finally {
            lock.writeLock().unlock(); //writeLock is released
        }
    }


    // Explicit locking for write operations
    public void lock() {
        lock.writeLock().lock();
    }

    // Unlocking the account after completing the operations
    public void unlock() {
        lock.writeLock().unlock();
    }
}