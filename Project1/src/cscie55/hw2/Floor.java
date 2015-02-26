package cscie55.hw2;

/**
 * Created by Isaac on 2/22/2015.
 */
public class Floor {
    private int numPassengers;
    private int floorNumber;
    private Building building;
    protected boolean needsStop = false;

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
        numPassengers = 0;
    }

    /**
     * Returns the number of passengers on this floor that are waiting for the Elevator.
     * @return numPassengers the number of passengers waiting for the Elevator.
     */
    public int passengersWaiting(){
        return numPassengers;
    }

    /**
     * Increase the number of passengers waiting on this floor by 1 and mark this floor as requiring a stop.
     */
    public void waitForElevator(){
        numPassengers++;
        needsStop = true;
    }

    /**
     * Removes a single passenger from the floor. If that was the last passenger, sets the "stop here" flag to false.
     */
    public void boardPassenger(){
        numPassengers--;

        if(numPassengers == 0){
            needsStop = false;
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
            if(floor.floorNumber == floorNumber){
                throw new IllegalArgumentException("A Floor with this floor number already exists.");
            }
        }
        this.floorNumber = floorNumber;
    }
}
