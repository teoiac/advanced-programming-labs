import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Airport airport = new Airport(2, 1, 1);
        List<Flight> flights = new ArrayList<>();

        flights.add(new Flight("F1", new Airliner(), LocalTime.of(10, 0), LocalTime.of(10, 30)));
        flights.add(new Flight("F2", new Airliner(), LocalTime.of(10, 5), LocalTime.of(10, 35)));
        flights.add(new Flight("F3", new Freighter(), LocalTime.of(10, 15), LocalTime.of(10, 45)));
        flights.add(new Flight("F4", new Drone(), LocalTime.of(10, 20), LocalTime.of(10, 50)));
        flights.add(new Flight("F5", new Airliner(), LocalTime.of(10, 25), LocalTime.of(10, 55))); // Needs emergency runway
        flights.add(new Flight("F6", new Airliner(), LocalTime.of(10, 32), LocalTime.of(11, 0))); // Another emergency case
        flights.add(new Flight("F7", new Airliner(), LocalTime.of(10, 36), LocalTime.of(11, 20))); // Another emergency case
        flights.add(new Flight("F8", new Airliner(), LocalTime.of(10, 40), LocalTime.of(11, 30))); // Another emergency case
        Scheduling problem = new Scheduling(airport, flights);
        problem.assignRunways();
    }
}
