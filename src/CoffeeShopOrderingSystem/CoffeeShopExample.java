//UoW No- W1867117

package CoffeeShopOrderingSystem;

public class CoffeeShopExample {
    public static void main(String[] args) {
        final int queueCapacity = 3; // Defines the maximum size of the queue for orders
        final int numOfCustomers = 5;  // Total number of customer threads
        final int numOfBaristas = 2;   // Total number of barista threads

        //Coffee shop instance is created with the specified queue capacity
        CoffeeShop coffeeShop = new CoffeeShop(queueCapacity);
        Thread[] threads = new Thread[numOfCustomers + numOfBaristas]; //Array is created to hold all the customer and barista threads

        // Initializing the customer threads
        for (int i = 0; i < numOfCustomers; i++) {
            threads[i] = new Thread(new Customer(coffeeShop, "Coffee-" + (i + 1)), "Customer-" + (i + 1));
        }

        // Initializing barista threads responsible for processing customer orders
        for (int i = 0; i < numOfBaristas; i++) {
            threads[numOfCustomers + i] = new Thread(new Barista(coffeeShop), "Barista-" + (i + 1));
        }

        // Starting all threads for both customers and baristas
        for (Thread thread : threads) {
            thread.start();
        }

        // To ensure all customers complete placing their orders before proceeding
        for (int i = 0; i < numOfCustomers; i++) {
            try {
                threads[i].join(); //join is used to wait for all the customer threads to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Close the coffee shop operations after customers are served
        coffeeShop.closeShop();

        // To ensure all barista to finish preparing the orders before processing
        for (int i = numOfCustomers; i < threads.length; i++) {
            try {
                threads[i].join(); //waiting for all the barista threads to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //displays that the Coffee Shop Simulation is complete
        System.out.println("Coffee shop simulation completed.");
    }
}