package cscie55.hw1.elevator;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-09
 */
public class ElevatorTest {

    public static void main(String[] args) {
        Elevator elevator = new Elevator();

        elevator.boardPassengers(3,2);

        elevator.boardPassenger(5);

//        @TODO Fix array indexing bug here
        for(int i=1;i<elevator.getNumFloors()+1;i++){
            System.out.println(elevator.toString());
//            System.out.println("Floor " + i + ": " + elevator.getNumPassengers());
            elevator.move();
        }
    }
}
