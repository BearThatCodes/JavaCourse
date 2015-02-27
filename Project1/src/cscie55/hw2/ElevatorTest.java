package cscie55.hw2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */

public class ElevatorTest {

    Building building = new Building();
    Elevator elevator;

    @Before
    public void setUp() throws Exception {
        elevator = new Elevator(building);
    }

    @After
    public void tearDown() throws Exception {
        elevator = null;
    }

    @Test
    public void testGetDirection() throws Exception {
        assertTrue("The elevator should be going down",elevator.getDirection() == -1);
    }

    @Test
    public void testCurrentFloor() throws Exception {
        assertTrue("The default first floor is 0",elevator.currentFloor() == 0);
        assertTrue("This should be the same as the default floor + 1",elevator.currentFloor(true) == 1);
    }

    @Test
    public void testGetFloors() throws Exception {
        assertTrue("The floors array (" + elevator.getFloors().length + ") should be the same size as the FLOORS constant (" + Building.FLOORS + ")",elevator.getFloors().length == Building.FLOORS);
    }

    @Test
    public void testMove() throws Exception {
        /*Add three passengers on floor 0 that are waiting for the elevator*/
        building.getFloor(0).waitForElevator();
        building.getFloor(0).waitForElevator();
        building.getFloor(0).waitForElevator();

        /*Board a passenger for floor 2, 1 indexed*/
        elevator.boardPassenger(2);

        /*Board a passenger for floor 1, 1 indexed*/
        elevator.boardPassenger(1);

        /*Board a passenger for floor 6, 1 indexed*/
        elevator.boardPassenger(6);

        assertTrue("There should be 3 passengers on the elevator and none waiting on floor 0",elevator.passengers() == 3 && building.getFloor(0).passengersWaiting() == 0);

        /*To Floor 2*/
        elevator.move();

        assertTrue("The direction should be 1 but is " + elevator.getDirection(),elevator.getDirection() == 1);

        assertTrue("The current floor should be 2 but is " + elevator.currentFloor(true),elevator.currentFloor(true) == 2);

        assertTrue("The number of passengers on the elevator should be 2 but is " + elevator.passengers(), elevator.passengers() == 2);

        /*Add two passengers to floor 3 (1 indexed) before we arrive there*/
        building.getFloor(2).waitForElevator();
        building.getFloor(2).waitForElevator();

        assertTrue("There should be 2 passengers waiting on floor 3 (2 if 0 indexed), but there are " + building.getFloor(2).passengersWaiting(), building.getFloor(2).passengersWaiting() == 2);

        /*To Floor 3*/
        elevator.move();

        assertTrue("1 passenger should have left, leaving us with 1 total, but there are " + elevator.passengers() + " on the elevator",elevator.passengers() == 1);

        assertTrue("There should be 2 passengers waiting on floor 3 (1 indexed) but there are " + building.getFloor(2).passengersWaiting(),building.getFloor(2).passengersWaiting() == 2);

        /*Add 12 passengers to floor 4 (1 indexed)*/
        for(int i=1;i<=12;i++){
            building.getFloor(3).waitForElevator();
            assertTrue("There should be " + (i) + " passengers waiting on floor 4, but there are " + building.getFloor(3).passengersWaiting(), (i) == building.getFloor(3).passengersWaiting());
        }

        /*To Floor 4*/
        elevator.move();

        assertTrue("There should be no passengers waiting on floor 3 (2 if 0 indexed), but there are " + building.getFloor(2).passengersWaiting(), building.getFloor(2).passengersWaiting() == 0);

        assertTrue("The current floor should be 4 but is " + elevator.currentFloor(true), elevator.currentFloor(true) == 4);

        /*To Floor 5*/
        elevator.move();

        assertTrue("There should be the maximum number of passengers, but there are " + elevator.passengers(),elevator.passengers() == Elevator.CAPACITY);

        assertTrue("The current floor should be 5", elevator.currentFloor(true) == 5);

        /*Move the Elevator up to floor 7, back down to floor 1, and then start back up, ending on floor 2*/
        for(int i=6;i<=2;i++){
            elevator.move();
        }
    }

    @Test
    public void testBoardPassenger() throws Exception {
        /*Put a passenger on floor 0 that is waiting for the Elevator*/
        building.getFloor(0).waitForElevator();

        /*Board a passenger for floor 2*/
        elevator.boardPassenger(2);

        assertTrue("The number of passengers bound for floor 2 should be 1 but is " + elevator.passengers(2),elevator.passengers(2) == 1);
    }
}