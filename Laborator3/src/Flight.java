import java.time.LocalTime;

class Flight {
    private String flightId;
    private Aircraft aircraft;
    private LocalTime landingStart;
    private LocalTime landingEnd;

    public Flight(String flightId, Aircraft aircraft, LocalTime landingStart, LocalTime landingEnd) {
        this.flightId = flightId;
        this.aircraft = aircraft;
        this.landingStart = landingStart;
        this.landingEnd = landingEnd;
    }

    public String getFlightId() {
        return flightId;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public LocalTime getLandingStart() {
        return landingStart;
    }

    public LocalTime getLandingEnd() {
        return landingEnd;
    }

    public boolean conflictsWith(Flight other) {
        if(this.landingEnd.isBefore(other.landingStart) || this.landingStart.isAfter(other.landingEnd)){
            return false;
        }
        return true;
    }
}
