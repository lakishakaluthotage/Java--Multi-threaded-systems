//UoW No- W1867117

package SharedBathroomStall;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

// FloorBathRoom class to define the bathroom simulation
public class FloorBathRoom {
    // Constants for the number of stalls and users
    final int bathroomStalls; //number of stalls available in the bathroom
    final int num_employees; //total number of users(employees/students) using the bathroom

    // Semaphore to control access to bathroom stalls
    private final Semaphore stalls;

    // Parameterized constructor to initialize the bathroom
    public FloorBathRoom(int bathroomStalls, int num_employees) {
        this.bathroomStalls = bathroomStalls;
        this.num_employees = num_employees;
        this.stalls = new Semaphore(bathroomStalls, true); // Semaphore is initialized with the number of stalls
        // Value true makes the semaphore fair, ensures threads are served in FIFO order
    }

    // Method representing a person using the bathroom
    public void useBathroom(String userName) {
        try {
            System.out.println(userName + " is waiting for a stall...");
            stalls.acquire(); // Acquire a stalls ( semaphore count is decreased)

            //prints that a user has acquired a stall and using it
            System.out.println(userName + " is using a stall.");
            // Simulate time taken to use the bathroom (random time between 1 sec to 5 secs)
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));

            System.out.println(userName + " has left the stall.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally { // always release the lock even an exception occurs
            stalls.release(); // Release the stall for other users (Increase the semaphore count)
        }
    }
}