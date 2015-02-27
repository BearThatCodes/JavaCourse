package cscie55.hw2;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestElevatorSimulation.class);
        for(Failure fail:result.getFailures()){
            System.out.println(fail.toString());
        }
        if(result.wasSuccessful()){
            System.out.println("All " + result.getRunCount() + " tests completed successfully in " + (result.getRunTime() / 1000.0000) + " seconds.");
        }
        else{
            System.out.println("Test suite failed.");
        }
    }
}
