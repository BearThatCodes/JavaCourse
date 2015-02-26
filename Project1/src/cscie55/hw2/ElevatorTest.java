package cscie55.hw2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertTrue("The floors array (" + elevator.getFloors().length + ") should be the same size as the FLOORS constant (" + building.FLOORS + ")",elevator.getFloors().length == building.FLOORS);
    }

    @Test
    public void testMove() throws Exception {
        /*Add two passengers on floor 0 that are waiting for the elevator*/
        building.getFloor(0).waitForElevator();
        building.getFloor(0).waitForElevator();

        /*Board a passenger for floor 2*/
        elevator.boardPassenger(2);

        /*Board a passenger for floor 1*/
        elevator.boardPassenger(1);

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

        elevator.move();

        assertTrue("The direction should be 1 but is " + elevator.getDirection(),elevator.getDirection() == 1);

        assertTrue("The current floor should be 1 but is " + elevator.currentFloor(),elevator.currentFloor() == 1);

        assertTrue("The number of passengers on the elevator should be 1 but is " + elevator.passengers(),elevator.passengers() == 1);

        /*Add two passengers to floor 2 before we arrive there*/
        building.getFloor(2).waitForElevator();
        building.getFloor(2).waitForElevator();

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

        elevator.move();

        assertTrue("1 passenger should have left, and two should have boarded, leaving us with 2 total",elevator.passengers() == 2);

        assertTrue("There should be no passengers waiting on floor 4",building.getFloor(4).passengersWaiting() == 0);

        /*Add 10 passengers to floor 3*/
        for(int i=0;i<12;i++){
            building.getFloor(3).waitForElevator();
            assertTrue("There should be " + (i+1) + " passengers waiting on floor 4, but there are " + building.getFloor(4).passengersWaiting(), (i+1) == building.getFloor(3).passengersWaiting());
        }

        System.out.println("There are " + building.getFloor(3).passengersWaiting() + " passengers on floor 3");

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

        elevator.move();

        /*assertTrue("There should be the maximum number of passengers, but there are " + elevator.passengers(),elevator.passengers() == elevator.CAPACITY);*/

        assertTrue("The current floor should be 3",elevator.currentFloor() == 3);

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

        elevator.move();

        assertTrue("The current floor should be 4",elevator.currentFloor() == 4);

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

        elevator.move();

        System.out.println(elevator.toString() + " in elevator, " + building.getFloor(elevator.currentFloor()).passengersWaiting() + " waiting");

    }

    @Test
    public void testBoardPassenger() throws Exception {
        /*Put a passenger on floor 0 that is waiting for the Elevator*/
        building.getFloor(0).waitForElevator();

        /*Board a passenger for floor 2*/
        elevator.boardPassenger(2);

        assertTrue("The number of passengers bound for floor 2 should be 1 but is " + elevator.passengers(2),elevator.passengers(2) == 1);
    }

    @Test
    public void testToString() throws Exception {

    }
}