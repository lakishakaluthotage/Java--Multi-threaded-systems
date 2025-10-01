//UoW No- W1867117

package SharedBathroomStall;

// Main class to simulate the use of shared bathroom stalls
public class SharedBathroomSimulation {
    public static void main(String[] args) {
        // Define the number of bathroom stalls and the total number of users
        final int bathroomStalls = 6; // Number of available bathroom stalls
        final int maxUsers = 100; // Total number of users (employees and students)

        // FloorBathRoom instance is created to manage the bathroom usage
        FloorBathRoom floorBathroom = new FloorBathRoom(bathroomStalls, maxUsers);

        // An array is created to hold Thread objects for each user
        Thread[] people = new Thread[maxUsers];

        // Loop to initialize and start threads for each user
        for (int i = 0; i < maxUsers; i++) {
            // Alternate between "Employee" and "Student" user types
            String userType = (i % 2 == 0) ? "Employee" : "Student";

            // Create a Runnable task for the user (PersonThread)
            Runnable personTask = new PersonThread(floorBathroom, userType + "-" + (i + 1));

            // Wrap the Runnable task in a Thread object
            people[i] = new Thread(personTask);

            // Start the thread to simulate bathroom usage
            people[i].start();
        }

        // Loop to ensure all threads finish execution before proceeding
        for (Thread person : people) {
            try {
                person.join(); // Wait for the current thread to finish
            } catch (InterruptedException e) {
                // If a thread is interrupted, restore the interrupted status and print an error
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        // Print a final message once all users have finished using the bathroom
        System.out.println("Simulation complete. All users have finished using the bathroom.");
        }
}