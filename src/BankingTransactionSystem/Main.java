//UoW No- W1867117

package BankingTransactionSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create the Bank Accounts with initial balances
        BankAccount account1 = new BankAccount(1, 6500);
        BankAccount account2 = new BankAccount(2, 1500);
        BankAccount account3 = new BankAccount(3, 3450);

        // Initialize the Transaction System a list is created backed by an array
        TransactionSystem transactionSystem = new TransactionSystem(Arrays.asList(account1, account2, account3));

        // Create threads to perform transactions
        Thread t1 = new Thread(() -> transactionSystem.transfer(1, 2, 150)); // t1 transfers $150 from Account 1 to Account 2
        Thread t2 = new Thread(() -> transactionSystem.transfer(2, 3, 280)); // t2 transfers $280 from Account 2 to Account 3
        Thread t3 = new Thread(() -> transactionSystem.transfer(3, 1, 450));  // t3 transfers $450 from Account 3 to Account 1
        Thread t4 = new Thread(() -> {
            // Concurrently reads the balances if Account 1 to 3
            System.out.println("Balance of Account 1: " + account1.getBalance()); //Prints the balance of Account 1
            System.out.println("Balance of Account 3: " + account3.getBalance()); //Prints the balance of Account 3
        });

        // Start all threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Waiting for all threads to complete their execution
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final account balances after all the transactions are done
        transactionSystem.printAccountBalances();

        // Simulate a transaction reversal
        transactionSystem.reverseTransaction(1, 2, 150); // Reverse the transfer of $150 from Account 1 to Account 2
        transactionSystem.printAccountBalances(); //Prints the account balances after the reversal
    }
}