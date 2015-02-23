package cscie55.hw2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isaac on 2/22/2015.
 */
public class Building {
    public static final int FLOORS = 7;
    private final Elevator elevator;
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
     * Returns the Elevator associated with this Building.
     * @return elevator the Elevator associated with this Building.
     */
    public Elevator elevator(){
        return elevator;
    }
}
