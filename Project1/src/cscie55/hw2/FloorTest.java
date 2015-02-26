package cscie55.hw2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FloorTest {

    Building building;
    Floor floor;

    @Before
    public void setUp() throws Exception {
        building = new Building();
        floor = new Floor(building,0);
    }

    @After
    public void tearDown() throws Exception {
        building = null;
        floor = null;
    }

    @Test
    public void testPassengersWaiting() throws Exception {
        assertTrue("The number of waiting passengers should be 0",floor.passengersWaiting()==0);
    }

    @Test
    public void testWaitForElevator() throws Exception {
        floor.waitForElevator();

        assertTrue("The number of waiting passengers should be 1",floor.passengersWaiting()==1);

        assertTrue("The waiting flag should be set to true",floor.needsStop);
    }
}