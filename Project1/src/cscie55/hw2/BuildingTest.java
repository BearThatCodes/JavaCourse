package cscie55.hw2;

import cscie55.hw2.Building;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildingTest {

    Building building;

    @Before
    public void setUp() throws Exception {
        building = new Building();
    }

    @After
    public void tearDown() throws Exception {
        building = null;
    }

    @Test
    public void testCreateBuilding() throws Exception {
        assertEquals("The number of floors in the ArrayList should equal the FLOORS constant.",building.FLOORS,building.floors.size());
    }

    @Test
    public void testReturnElevator() throws Exception{
        assertNotNull("The method should return an Elevator object.",building.elevator());
    }
}