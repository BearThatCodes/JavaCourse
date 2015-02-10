package cscie55.hw1.elevator;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-09
 */
public class Elevator {
    private static final int numFloors = 7;
//    1=up, -1=dow, 0=not moving
    private int direction;
    private int currFloor;
//    Array of size numFloors containing arrays of size 2 with numPassengers and stop yes/no
    private int[][] floors;

    /**
     * Creates a new Elevator
     */
    public Elevator() {
        direction = 0;
        currFloor = 1;
        /* Creates an empty array to hold the number of floors in the building
        *  and two pieces of information for each floor, the number of passengers
        *  destined for that floor and whether a stop was requested.*/
        floors = new int[numFloors][2];
    }

    /**
     * Creates a new Elevator
     * @param direction the direction of the Elevator, -1 means down, 1 means up, and 0 means it currently has no direction
     * @param currFloor the floor on which the Elevator will start
     */
    public Elevator(int direction, int currFloor) {
        this.direction = direction;
        this.currFloor = currFloor;
        floors = new int[numFloors][2];
    }

    /**
     * @return numFloors the number of floors in the building
     */
    public static int getNumFloors() {
        return numFloors;
    }

    /**
     *
     * @return direction the current direction of the elevator ()
     */
    public int getDirection() {
        return direction;
    }

    /**
     *
     * @return currFloor the floor the Elevator is currently on
     */
    public int getCurrFloor() {
        return currFloor;
    }

    /**
     *
     * @return floors the array containing floor and passenger information for this Elevator
     * @see Elevator#Elevator(int direction, int currFloor)
     */
    public int[][] getFloors() {
        return floors;
    }
}
