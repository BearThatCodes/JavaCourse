package cscie55.hw3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Floor {
    private int floorNumber;
    private Building building;
    //We use a LinkedList because it implements the Queue interface, providing desired functionality
    private LinkedList<Passenger> passengersGoingUp;
    private LinkedList<Passenger> passengersGoingDown;
    private LinkedList<Passenger> passengersResident;

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
        passengersGoingUp = new LinkedList<Passenger>();
        passengersGoingDown = new LinkedList<Passenger>();
        passengersResident = new LinkedList<Passenger>();
    }

    /**
     * Increase the number of passengers waiting on this floor by 1 and mark this floor as requiring a stop.
     */
    public void waitForElevator(Passenger passenger,int destinationFloor){
        if(destinationFloor > floorNumber){
            passengersGoingUp.add(passenger);
        }
        else if(destinationFloor < floorNumber){
            passengersGoingDown.add(passenger);
        }
        else{
            //If they're waiting to reach this floor, then why not just have them become resident?
            passengersResident.add(passenger);
        }
    }

    /**
     * Indicates if the indicated Passenger is resident on this Floor
     * @param passenger the Passenger object to check
     * @return boolean TRUE if the Passenger is resident, FALSE otherwise
     */
    public boolean isResident(Passenger passenger){
        /*
        If the following things are true, return true:
        Passenger is on the current floor
        Passenger has no destinationFloor (and so is not in either waiting queue)
        Floor has at least one resident Passenger
         */
        if(passenger.currentFloor() == floorNumber && passenger.destinationFloor() == Passenger.UNDEFINED_FLOOR && passengersResident.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void enterGroundFloor(Passenger passenger){
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

                if(passengersGoingUp.peek().equals(null)){
                    throw new IllegalStateException("We are trying to board a Passenger going up, but there are no Passengers on this Floor.");
                }

                return passengersGoingUp.poll();
            case DOWN:
                if(passengersGoingDown.size() == 0){
                    passengersGoingDown.add(new Passenger(floorNumber));
                }

                if(passengersGoingDown.peek().equals(null)){
                    throw new IllegalStateException("We are trying to board a Passenger going down, but there are no Passengers on this Floor.");
                }

                return passengersGoingDown.poll();
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
