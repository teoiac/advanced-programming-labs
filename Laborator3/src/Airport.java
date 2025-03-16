import java.util.ArrayList;
import java.util.List;

class Airport {
    private final List<Runway> airlinerRunways = new ArrayList<>();
    private final List<Runway> cargoRunways = new ArrayList<>();
    private final List<Runway> droneRunways = new ArrayList<>();
    private final Runway emergencyRunway = new Runway(RunwayType.EMERGENCY_RUNWAY);

    public Airport(int airlinerCount, int cargoCount, int droneCount) {
        for (int i = 0; i < airlinerCount; i++) airlinerRunways.add(new Runway(RunwayType.AIRLINER_RUNWAY));
        for (int i = 0; i < cargoCount; i++) cargoRunways.add(new Runway(RunwayType.CARGO_RUNWAY));
        for (int i = 0; i < droneCount; i++) droneRunways.add(new Runway(RunwayType.DRONE_RUNWAY));
    }

    private List<Runway> getRunwaysForFlight(Flight flight) {
        if (flight.getAircraft() instanceof Airliner) return airlinerRunways;
        if (flight.getAircraft() instanceof Freighter) return cargoRunways;
        if (flight.getAircraft() instanceof Drone) return droneRunways;
        return new ArrayList<>();
    }

    public Runway getAvailableRunway(Flight flight) {
        List<Runway> runways = getRunwaysForFlight(flight);

        for (Runway runway : runways) {
            if (runway.isAvailable(flight)) {
                return runway;
            }
        }


        if (emergencyRunway.isAvailable(flight)) {
            return emergencyRunway;
        }

        return null; // r.i.p
    }
}
