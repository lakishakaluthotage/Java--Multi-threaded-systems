//UoW No- W1867117

package CoffeeShopOrderingSystem;

// Customer Thread - Implementing Runnable Interface to create a Thread
public class Customer implements Runnable {
    // Declared the Instance Variables - Encapsulation
    private final CoffeeShop coffeeShop;
    private final String order;

    // Parameterized Constructor to set the initial instance values
    public Customer(CoffeeShop coffeeShop, String order) {
        this.coffeeShop = coffeeShop;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            coffeeShop.placeOrder(order); // Place the customer order into the coffee shop
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // If the thread is interrupted it restores the interrupt status
        }
    }
}