package org.example;

import java.util.List;

public class Robot {
    private String name;
    private String model;
    private Route trail;
    private String currentLocation;

    public Robot(String name, String model, Route trail) {
        this.name = name;
        this.model = model;
        this.trail = trail;
        this.currentLocation = null;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void moveTo(String destination) {
        if (currentLocation == null) {
            System.out.println("No starting location specified");
            return;
        }

        List<String> path = trail.getShortestPath(currentLocation, destination);
        if (path.isEmpty()) {
            System.out.println("No available path from " + currentLocation + " to " + destination);
            return;
        }

        System.out.println("Robot " + name + " is moving:");
        for (String location : path) {
            System.out.println("-> " + location);
            currentLocation = location;
        }
        System.out.println("Robot reached the destination" + currentLocation);
    }

}
