import java.util.Comparator;
import java.util.List;

class Scheduling {
    private final Airport airport;
    private final List<Flight> flights;

    public Scheduling(Airport airport, List<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
    }

    public void assignRunways() {
        flights.sort(Comparator.comparing(Flight::getLandingStart));

        for (Flight flight : flights) {
            Runway assignedRunway = airport.getAvailableRunway(flight);

            if (assignedRunway != null) {
                assignedRunway.assignFlight(flight);
                System.out.println("Flight " + flight.getFlightId() + " assigned to " + assignedRunway.getType());
            } else {
                System.out.println("Flight " + flight.getFlightId() + " must wait for an empty runway");
            }
        }
    }
}
