package cscie55.hw3;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Floor {
    private int floorNumber;
    private Building building;
    //We use a LinkedList because it implements the Queue interface, providing desired functionality
    protected HashSet<Passenger> passengersGoingUp;
    protected HashSet<Passenger> passengersGoingDown;
    private HashSet<Passenger> passengersResident;

    /**
     * Creates a new Floor tied to the specified Building and with a given floorNumber.
     * @param building the Building with which this Floor is associated
     * @param floorNumber an integer indicating the floor of the Building which this Floor should represent
     */
    public Floor(Building building,int floorNumber){
        this.building = building;
        try{
            assignFloorNumber(floorNumber);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        passengersGoingUp = new HashSet<Passenger>();
        passengersGoingDown = new HashSet<Passenger>();
        passengersResident = new HashSet<Passenger>();
    }

    /**
     * Increase the number of passengers waiting on this floor by 1 and mark this floor as requiring a stop.
     */
    public void waitForElevator(Passenger passenger,int destinationFloor){
        if(destinationFloor > floorNumber){
            passengersGoingUp.add(passenger);
            passengersResident.remove(passenger);
            passenger.waitForElevator(destinationFloor);
            System.out.println("There are now " + passengersGoingUp.size() + " passengers going up on floor " + floorNumber);
        }
        else if(destinationFloor < floorNumber){
            passengersGoingDown.add(passenger);
            passengersResident.remove(passenger);
            passenger.waitForElevator(destinationFloor);
            System.out.println("There are now " + passengersGoingDown.size() + " passengers going down on floor " + floorNumber);
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
        System.out.println("There are now " + passengersResident.size() + " passengers resident on floor " + floorNumber);
        passengersResident.add(passenger);
    }

    /**
     * Removes a single passenger from the floor. If that was the last passenger, sets the "stop here" flag to false.
     * @return passenger the Passenger object that is boarding the Elevator
     */
    public Passenger boardPassenger(Elevator.Direction direction){
        switch (direction) {
            case UP:
                if(passengersGoingUp.size() == 0){
                    passengersGoingUp.add(new Passenger(floorNumber));
                }

                Passenger upPassenger = passengersGoingUp.iterator().next();

                passengersGoingUp.iterator().remove();

                System.out.println("Returning passenger: " + upPassenger);
                return upPassenger;
            case DOWN:
                if(passengersGoingDown.size() == 0){
                    passengersGoingDown.add(new Passenger(floorNumber));
                }

                Passenger downPassenger = passengersGoingDown.iterator().next();

                passengersGoingUp.iterator().remove();

                System.out.println("Returning passenger: " + downPassenger);
                return downPassenger;
            case NONE:
                throw new IllegalArgumentException("The Elevator must have a Direction in order to board a Passenger.");
            default:
                throw new IllegalArgumentException("The Direction must be either Up or Down.");
        }
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
}
