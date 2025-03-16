class Runway {
    private final RunwayType type;
    private Flight currentFlight;

    public Runway(RunwayType type) {
        this.type = type;
    }

    public boolean isAvailable(Flight flight) {
        return currentFlight == null || currentFlight.getLandingEnd().isBefore(flight.getLandingStart());
    }

    public void assignFlight(Flight flight) {
        if (isAvailable(flight)) {
            currentFlight = flight;
        } else {
           return;
        }
    }

    public RunwayType getType() {
        return type;
    }
}
