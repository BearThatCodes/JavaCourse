package cscie55.hw3;
import java.util.HashSet;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Elevator {
    public static final int CAPACITY = 10;
    private Direction direction;
    private int currFloor;
    private int[][] floors;
    private HashSet<Passenger> passengers;
    private Building building;
    public enum Direction {UP,DOWN,NONE}

    /**
     * Creates a new Elevator with default values
     */
    public Elevator(Building building) {
        direction = Direction.DOWN;
        currFloor = 0;
        /* Creates an empty array to hold the number of floors in the building
        *  and two pieces of information for each floor, the number of passengers
        *  destined for that floor and whether a stop was requested. The first
        *  element in the subarray is the number of passengers, an integer >= 0.
        *  The second element is the "stop here" flag, indicating whether the
        *  Elevator should stop (0 means no, 1 means yes).*/
        floors = new int[Building.FLOORS][2];
        this.building = building;
        passengers = new HashSet<Passenger>();
    }

    /**
     * Returns TRUE if the Elevator is currently going up, and FALSE otherwise.
     * @return boolean TRUE if the Elevator is going up
     */
    public boolean goingUp(){
        return direction == Direction.UP;
    }

    /**
     * Returns TRUE if the Elevator is currently going down, and FALSE otherwise.
     * @return boolean TRUE if the Elevator is going down
     */
    public boolean goingDown(){
        return direction == Direction.DOWN;
    }

    /**
     * @return currFloor the floor the Elevator is currently on (0 indexed)
     */
    public int currentFloor() {
        return currFloor + 1;
    }

    /**
     * @return floors the array containing floor and passenger information for this Elevator
     */
    public int[][] getFloors() {
        return floors;
    }

    /**
     * Returns an ArrayList of Passengers that are currently on the Elevator
     *
     * @return passengers an ArrayList of Passengers that are currently on the Elevator
     */
    public HashSet<Passenger> passengers() {
        return passengers;
    }

    /**
     * Switches the direction of the Elevator. If the Elevator was going up, it will now be going down, and if it was going down, it will now be going up. If the Elevator did not have a direction, the direction will not be changed.
     *
     * @return direction the new direction of the Elevator
     */
    private Direction toggleDirection() {
        if (goingUp()) {
            direction = Direction.DOWN;
        }
        else if (goingDown()) {
            direction = Direction.UP;
        }
        else {
            direction = Direction.NONE;
        }

        return direction;
    }

    /**
     * Moves the Elevator, making appropriate changes to state(
     * Change Elevator direction if necessary,
     * Change current floor,
     * Disembark all passengers on the new floor
     * Board all passengers that are waiting on the current floor, provided there is room)
     */
    public void move() {
        /*If the Elevator is at the top or bottom floor, switch directions*/
        if (currFloor == 0 || currFloor == Building.FLOORS - 1) {
            toggleDirection();
        }

        /*Increase or decrease the floor as appropriate*/
        if (goingUp()) {
            currFloor++;
        }
        else if (goingDown()) {
            currFloor--;
        }
        else {
            throw new UnsupportedOperationException("Cannot move Elevator without a direction. Direction is currently " + direction + ".");
        }

        /*Clear the passengers destined for this floor.*/
        disembark(currFloor);

        /*Board any waiting passengers*/
        Floor floorObject = building.floor(currFloor + 1);

        if(goingUp()){
            while(floorObject.passengersGoingUp.size() > 0 && passengers().size() != CAPACITY){
                try{
                    boardPassenger(1);
                }
                catch (ElevatorFullException e){
                    System.out.println("The Elevator is currently full but will keep trying to board the passengers that are waiting on floor " + currFloor + " each time it arrives on this floor.");
                }
            }
        }

        if(goingDown()){
            while(floorObject.passengersGoingDown.size() > 0 && passengers().size() != CAPACITY){
                try{
                    boardPassenger(1);
                }
                catch (ElevatorFullException e){
                    System.out.println("The Elevator is currently full but will keep trying to board the passengers that are waiting on floor " + currFloor + " each time it arrives on this floor.");
                }
            }
            }

    }

    private void disembark(int floor){
        floors[floor][0] = 0;
    }

    /**
     * Adds 1 new passenger to the Elevator, destined for the specified floor
     *
     * @param floor the floor for which the passenger is destined
     */
    public void boardPassenger(int floor) throws ElevatorFullException {
        if (passengers().size() + 1 > CAPACITY) {
            throw new ElevatorFullException(this);
        }
        passengers.add(building.floor(floor).boardPassenger(direction));
    }

    public String toString() {
        return "Floor " + currentFloor() + ": " + passengers() + " passengers";
    }
}
