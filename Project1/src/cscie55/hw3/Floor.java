package cscie55.hw3;

import java.util.LinkedHashSet;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Floor {
    private int floorNumber;
    private Building building;
    //We use a LinkedList because it implements the Queue interface, providing desired functionality
    protected LinkedHashSet<Passenger> passengersGoingUp;
    protected LinkedHashSet<Passenger> passengersGoingDown;
    private LinkedHashSet<Passenger> passengersResident;

    /**
     * Creates a new Floor tied to the specified Building and with a given floorNumber.
     * @param building the Building with which this Floor is associated
     * @param floorNumber an integer indicating the floor of the Building which this Floor should represent
     */
    public Floor(Building building,int floorNumber){
        this.building = building;
        assignFloorNumber(floorNumber);
        passengersGoingUp = new LinkedHashSet<Passenger>();
        passengersGoingDown = new LinkedHashSet<Passenger>();
        passengersResident = new LinkedHashSet<Passenger>();
    }

    /**
     * Increase the number of passengers waiting on this floor by 1 and mark this floor as requiring a stop.
     */
    public void waitForElevator(Passenger passenger,int destinationFloor){
        if(destinationFloor > floorNumber + 1){
            passengersGoingUp.add(passenger);
            passengersResident.remove(passenger);
            passenger.waitForElevator(destinationFloor);
        }
        else if(destinationFloor < floorNumber + 1){
            passengersGoingDown.add(passenger);
            passengersResident.remove(passenger);
            passenger.waitForElevator(destinationFloor);
        }
        else{
            //If they're waiting to reach this floor, then why not just have them become resident?
            passengersResident.add(passenger);
            passenger.waitForElevator(Passenger.UNDEFINED_FLOOR);
        }
    }

    /**
     * Indicates if the indicated Passenger is resident on this Floor
     * @param passenger the Passenger object to check
     * @return boolean TRUE if the Passenger is resident, FALSE otherwise
     */
    public boolean isResident(Passenger passenger){
        return passengersResident.contains(passenger);
    }

    /**
     * Adds a new Passenger to this floor as a resident
     * @param passenger the Passenger to be added
     */
    public void enterGroundFloor(Passenger passenger){
        passenger.setCurrentFloor(floorNumber + 1);
        passengersResident.add(passenger);
    }

    /**
     * Removes a single passenger from the floor. If that was the last passenger, sets the "stop here" flag to false.
     * @return passenger the Passenger object that is boarding the Elevator
     */
    public Passenger boardPassenger(Elevator.Direction direction){
        Passenger returnPassenger = null;
        switch (direction) {
            case UP:
                if(passengersGoingUp.iterator().hasNext()) {
                    returnPassenger = passengersGoingUp.iterator().next();

                    passengersGoingUp.remove(returnPassenger);

                    returnPassenger.boardElevator();
                }
                break;
            case DOWN:
                if(passengersGoingDown.iterator().hasNext()){
                    returnPassenger = passengersGoingDown.iterator().next();

                    passengersGoingDown.remove(returnPassenger);

                    returnPassenger.boardElevator();
                }
                break;
            case NONE:
                throw new IllegalArgumentException("The Elevator must have a Direction in order to board a Passenger.");
            default:
                throw new IllegalArgumentException("The Direction must be either Up or Down.");
        }

        return returnPassenger;
    }

    /**
     * Assigns the floor number to this Floor if the proposed floor number is valid (within the acceptable range and not already used in this Building).
     * @param floorNumber the floor number to check
     * @throws IllegalArgumentException
     */
    private void assignFloorNumber(int floorNumber) throws IllegalArgumentException{
        if(floorNumber < 0 || floorNumber > Building.FLOORS){
            throw new IllegalArgumentException("The floor number must be between 0 and " + Building.FLOORS + " inclusive.");
        }
        for(Floor floor:building.floors){
            if(floor.floorNumber == floorNumber && floorNumber != 0){
                throw new IllegalArgumentException("A Floor with floor number " + floorNumber + " already exists.");
            }
        }
        this.floorNumber = floorNumber;
    }

    public String toString(){
        return Integer.toString(floorNumber + 1);
    }
}
