//UoW No- W1867117

package CoffeeShopOrderingSystem;

//The Barista class represents the thread that processes customer orders in the Coffee Shop
public class Barista implements Runnable {
    private final CoffeeShop coffeeShop; //Shared Resource

    // Parameterized Constructor to set the initial instance values for the coffeeShop
    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Fetch an order from the Coffee Shop for preparation
                String order = coffeeShop.prepareOrder();
                // If no more orders are available the thread is terminated. (Coffee Shop: closed), stop processing
                if (order == null) {
                    System.out.println(Thread.currentThread().getName() + " is stopping.");
                    break;
                }
                // Here it simulates the time taken to prepare an order
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruptions and terminate gracefully
        }
    }
}