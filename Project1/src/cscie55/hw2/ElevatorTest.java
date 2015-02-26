package cscie55.hw2;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-10
 */
public class ElevatorTest {
    public static void main(String[] args) {
        /*Creates a new Building object.*/
        Building building = new Building();
        /*Create a new Elevator object*/
        Elevator elevator = building.elevator();

        /*Board 2 passengers destined for floor 3*/
        try {
            elevator.boardPassenger(3);
        }
        catch (ElevatorFullException e){
            System.out.println(e.getMessage());
        }
        try{
            elevator.boardPassenger(3);
        }
        catch (ElevatorFullException e){
            System.out.println(e.getMessage());
        }

        /*Board 1 passenger destined for floor 5*/
        try {
            elevator.boardPassenger(5);
        }
        catch (ElevatorFullException e){
            System.out.println(e.getMessage());
        }

        /*Board a bunch of passengers destined for floor 6*/
       for(int i=0;i<8;i++){
            try {
                elevator.boardPassenger(7);
            }
            catch (ElevatorFullException e){
                System.out.println(e.getMessage());
            }
        }

        for (int i = 1; i < (Building.getNumFloors()) * 2; i++) {
            /*For each floor, print the current state.*/
            System.out.println(elevator.toString());
            /*Moves the Elevator to the next floor*/
            elevator.move();
        }
    }
}
