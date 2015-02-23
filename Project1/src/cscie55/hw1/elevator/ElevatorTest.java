package cscie55.hw1.elevator;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-10
 */
public class ElevatorTest {
    public static void main(String[] args) {
        /*Create a new Elevator object*/
        Elevator elevator = new Elevator();

        /*Board 2 passengers destined for floor 3*/
        elevator.boardPassenger(3, 2);

        /*Board 1 passenger destined for floor 5*/
        elevator.boardPassenger(5);

        for (int i = 1; i < (Elevator.getNumFloors()) * 2; i++) {
            /*For each floor, print the current state.*/
            System.out.println(elevator.toString());
            /*Moves the Elevator to the next floor*/
            elevator.move();
        }
    }
}
