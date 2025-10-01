//UoW No- W1867117

package SharedBathroomStall;

// PersonThread class to represent each user as a thread
public class PersonThread implements Runnable {
    private final FloorBathRoom bathroom; //Shared object reference
    private final String userName; //Thread identifier for the user

    // Parameterized Constructor to set the initial variable values
    public PersonThread(FloorBathRoom bathroom, String userName) {
        this.bathroom = bathroom; //Assigns the shared bathroom instance and the userName
        this.userName = userName;
    }

    // useBathroom is the main functionality
    @Override
    public void run() {
        bathroom.useBathroom(userName);
    }
}