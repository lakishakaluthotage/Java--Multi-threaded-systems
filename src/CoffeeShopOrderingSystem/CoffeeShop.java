//UoW No- W1867117

package CoffeeShopOrderingSystem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Monitor class representing the shared resource (Coffee Shop)
public class CoffeeShop {
    private final int maxOrderCapacity; // Maximum number of orders the queue can hold
    private final Queue<String> orderQueue = new LinkedList<>(); // Order queue initialized as a LinkedList
    private boolean openCoffeeShop = true; // Flag is set to value true to indicate if the coffee shop is open

    private final Lock lock = new ReentrantLock(); // Created a new Reentrant Lock for thread synchronization | Concurrency
    private final Condition fullQueue = lock.newCondition();   // Condition to manage full queue scenarios
    private final Condition emptyQueue = lock.newCondition();  // Condition to manage empty queue scenarios

    // Parameterized Constructor to set the initial order Capacity for the coffeeShop
    public CoffeeShop(int capacity) {
        this.maxOrderCapacity = capacity;
    }

    //Re-entrant Lock is used for Synchronization
    // placeOrder Method is used for customers to place an order
    public void placeOrder(String order) throws InterruptedException {
        lock.lock(); //Lock is acquired to ensure thread-safe access to the shared orderQueue
        try {
            //checking whether the orderQueue is greater than the maximum order capacity,
            // if so thread is sent to a waiting state
            while (orderQueue.size() >= maxOrderCapacity) {
                System.out.println(Thread.currentThread().getName() + " is waiting. Queue is full.");
                fullQueue.await(); // Wait until space is available
            }
            //else the order is added to the orderQueue
            orderQueue.add(order);
            System.out.println(Thread.currentThread().getName() + " placed order: " + order);
            // Notifying baristas to prepare orders and
            // customers waiting to place orders are notified
            emptyQueue.signalAll();
        } finally {
            lock.unlock();// Lock is released
        }
    }
    //Re-entrant Lock is used for Synchronization
    // Method for baristas to prepare an order
    public String prepareOrder() throws InterruptedException {
        lock.lock();// Lock is acquired
        try {
            //If the queue is empty,barista will wait until an order is placed
            while (orderQueue.isEmpty()) {
                if (!openCoffeeShop) return null; // Check if the coffee shop is closed
                System.out.println(Thread.currentThread().getName() + " is waiting. Queue is empty.");
                emptyQueue.await(); // Barista thread will wait on the emptyQueue condition until an order is added
            }
            //here the prepared order is retrieved and removed from the orderQueue
            String order = orderQueue.poll();
            System.out.println(Thread.currentThread().getName() + " prepared order: " + order);
            fullQueue.signalAll(); // Notify all the customers waiting in the fullQueue condition that space is available
            return order; //return the prepared order
        } finally {
            lock.unlock(); //Releasing the lock so other threads can access the queue
        }
    }

    // Method to close the coffee shop
    public void closeShop() {
        lock.lock();//Lock is acquired
        try {
            //Flag is set to value false, to indicate the coffee shop is closed
            openCoffeeShop = false;
            emptyQueue.signalAll(); // Wake up all waiting baristas threads and exit the waiting state gracefully
        } finally {
            lock.unlock(); // Release the lock in the finally block ensures and prevents deadlocks
        }
    }
}