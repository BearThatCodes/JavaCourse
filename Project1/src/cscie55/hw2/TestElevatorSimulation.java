package cscie55.hw2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BuildingTest.class,
        ElevatorFullExceptionTest.class,
        ElevatorTest.class,
        FloorTest.class
})

public class TestElevatorSimulation{

}
