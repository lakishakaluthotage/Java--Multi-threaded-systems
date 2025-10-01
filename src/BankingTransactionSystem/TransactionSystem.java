//UoW No- W1867117

package BankingTransactionSystem;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

class TransactionSystem {
    private final Map<Integer, BankAccount> accounts; //All the accounts are stored using their IDs

    // Constructor: Initialize the system with a list of BankAccounts
    public TransactionSystem(List<BankAccount> accountList) {
        accounts = new HashMap<>(); //HashMap is used to store accounts
        //Each account is mapped to the HashMap using the account ID as the key
        for (BankAccount account : accountList) {
            accounts.put(account.getId(), account);
        }
    }

    // transfer method transfer money between accounts safely
    public void transfer(int fromAccountId, int toAccountId, double amount) {
        // Check whether the account is a valid account
        if (!accounts.containsKey(fromAccountId) || !accounts.containsKey(toAccountId)) {
            //Print error message if either the account or the account ID is invalid
            System.out.println("Invalid account IDs.");
        }

        //Account objects are retrieved using the BankAccount
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        // Lock accounts in a consistent order to prevent deadlocks
        BankAccount firstLock = fromAccountId < toAccountId ? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        //Acquiring locks on both accounts
        firstLock.lock(); //Locks the first account
        secondLock.lock(); //Locks the second account
        try {
            //Check whether the fromAccount has sufficient balance for the transfer
            if (fromAccount.getBalance() < amount) {
                System.out.println("Insufficient funds in Account " + fromAccountId);
                return;
            }

            // Perform the transfer by withdrawing the amount from the fromAccount and depositing in the toAccount
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred $" + amount + " from Account "
                    + fromAccountId + " -> to Account " + toAccountId);

        } finally {
            // Unlock the locks in reverse order to avoid deadlocks
            secondLock.unlock();
            firstLock.unlock();
        }
    }

    // Method that reverse a transaction in case of an error
    public void reverseTransaction(int fromAccountId, int toAccountId, double amount) {
        System.out.println("|----- Reversing transaction -----|");
        transfer(toAccountId, fromAccountId, amount); //Perform the reverse transaction by swapping the IDs
    }

    // Prints the current account balances
    public void printAccountBalances() {
        System.out.println("|----- Account Balances -----|");
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + ": $" + account.getBalance());
        }
    }
}