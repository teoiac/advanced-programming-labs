package org.example;

public class Location implements Comparable<Location> {
    private String name;
    private final LocationType locationType;
    public Location(String name,LocationType locationType ) {
        this.name = name;
        this.locationType = locationType;
    }

    public String getName() {
        return name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    @Override
    public int compareTo(Location location) {
        return this.name.compareTo(location.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
