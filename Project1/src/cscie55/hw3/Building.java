package cscie55.hw3;

import java.util.ArrayList;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Building {
    public static final int FLOORS = 7;
    private Elevator elevator;
    protected ArrayList<Floor> floors = new ArrayList<Floor>(FLOORS);

    /**
     * Creates a new Building with default values.
     * Creates and assigns a new Elevator to the building.
     * Creates and assigns FLOORS new Floors to the Building.
     */
    public Building() {
        elevator = new Elevator(this);
        for(int i = 0;i<FLOORS;i++){
            floors.add(new Floor(this,i));
        }
    }

    /**
     * @return FLOORS the number of floors in the building
     */
    public static int getNumFloors() {
        return FLOORS;
    }

    /**
     * Returns the Elevator associated with this Building.
     * @return elevator the Elevator associated with this Building.
     */
    public Elevator elevator(){
        return elevator;
    }

    /**
     * Get the Floor object with the specified floor number
     * @param floorNumber the 0-indexed floor number of the Floor to be returned, must be between 0 and FLOORS-1 inclusive
     * @return floor the Floor object with the specified floor number
     */
    public Floor floor(int floorNumber) throws IllegalArgumentException{
        if(floorNumber < 1 || floorNumber >= FLOORS + 1){
            throw new IllegalArgumentException("The floor number " + floorNumber + " is invalid. Floor number must be between 1 and " + (FLOORS) + " inclusive.");
        }
        return floors.get(floorNumber - 1);
    }

    /**
     * Adds a new Passenger to the ground Floor
     * @param passenger the Passenger to be added
     */
    public void enter(Passenger passenger){
        floor(1).enterGroundFloor(passenger);
    }
}
