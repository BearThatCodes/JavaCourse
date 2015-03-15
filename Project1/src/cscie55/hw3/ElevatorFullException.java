package cscie55.hw3;

import cscie55.hw2.*;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class ElevatorFullException extends Exception{
    /**
     * Creates a new ElevatorFullException with a default message.
     */
    public ElevatorFullException(cscie55.hw2.Elevator elevator){
        super("Cannot board passenger(s). Adding the requested number of passengers would make the Elevator exceed its maximum capacity. There are currently " + elevator.passengers() + " out of " + cscie55.hw2.Elevator.CAPACITY + " passengers on the Elevator.");
    }

    /**
     * Creates a new ElevatorFullException with the specified error message.
     * @param message the error message that should be returned when this ElevatorFullException is thrown.
     */
    public ElevatorFullException(String message){
        super(message);
    }
}
