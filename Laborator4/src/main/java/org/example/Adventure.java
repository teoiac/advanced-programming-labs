package org.example;

import com.github.javafaker.Faker;
import org.graph4j.shortestpath.DijkstraShortestPathDefault;

import java.util.*;
import java.util.stream.Collectors;

public class Adventure {
    private List<Location> locations;
    private Route route;
    private Robot robot;
    private Faker faker;
    private Random random;

    public void generateLocations() {
        Set<String> usedNames = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String locationName =  faker.pokemon().location();
            LocationType type = LocationType.values()[random.nextInt(LocationType.values().length)];
            locations.add(new Location(locationName, type));
        }
    }

    public void connectLocations() {
        for (int i = 0; i < locations.size() - 1; i++) {
            String from = locations.get(i).getName();
            String to = locations.get(i + 1).getName();
            int travelTime = 2 + random.nextInt(10);
            route.addPath(from, to, travelTime);
        }
    }

    public Adventure() {
        this.faker = new Faker();
        this.random = new Random();
        this.locations = new ArrayList<>();
        generateLocations();
        this.route = new Route(locations);
        connectLocations();
        this.robot = new Robot("robby", "c3943", route);
    }

    public void printLocations() {
        System.out.println("Locations:");
        for (Location location : locations) {
            System.out.println(location.getName() + "->" + location.getLocationType());
        }
    }

    public void printFriendlyLocations() {
        TreeSet<Location> friendlyLocations = locations.stream()
                .filter(location -> location.getLocationType() == LocationType.FRIENDLY)
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
        System.out.println("Friendly Locations:");
        friendlyLocations.forEach(location -> System.out.println(location.getName()));
    }

    public void printEnemyLocations() {
        LinkedList<Location> enemyLocations = locations.stream()
                .filter(location -> location.getLocationType() == LocationType.ENEMY)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        System.out.println("Enemy Locations:");
        enemyLocations.forEach(location -> System.out.println(location.getName()));
    }

    public void printGeneratedEdges() {
        System.out.println("Generated Edges:");
        for (int i = 0; i < locations.size(); i++) {
            String from = locations.get(i).getName();
            for (int j = 0; j < locations.size(); j++) {
                if (i != j) {
                    String to = locations.get(j).getName();
                    double travelTime = route.getTravelTime(from, to);
                    if (travelTime != Double.POSITIVE_INFINITY)
                        System.out.println(from + " -> " + to + " -> " + (int) travelTime);
                }
            }
        }
    }

    public void navigateRobot() {
        String destination = locations.get(locations.size() - 1).getName();
        robot.setCurrentLocation(locations.get(0).getName());
        System.out.println("Robot navigating from " + robot.getCurrentLocation() + " to " + destination);
        robot.moveTo(destination);
    }

    public void computeAndDisplayFastestTimes() {
        Map<LocationType, List<Location>> locationsByType = locations.stream()
                .collect(Collectors.groupingBy(Location::getLocationType));

        System.out.println("\nFastest times to all locations:");

        Location startLocation = locations.get(0);
        DijkstraShortestPathDefault dijkstra = new DijkstraShortestPathDefault(route.getGraph(), route.getLocationIndex(startLocation.getName()));
        double[] shortestTimes = dijkstra.getPathWeights();

        Arrays.stream(LocationType.values()).forEach(type -> {
            System.out.println("\n" + type + " locations:");
            locationsByType.getOrDefault(type, Collections.emptyList()).forEach(location -> {
                Integer index = route.getLocationIndex(location.getName());
                double time =  shortestTimes[index];
                System.out.println(location.getName() + " - Time: " + time);
            });
        });
    }

    public void playAdventure() {
        printLocations();
        System.out.println();
        printFriendlyLocations();
        System.out.println();
        printEnemyLocations();
        System.out.println();
        printGeneratedEdges();
        System.out.println();
        navigateRobot();
        System.out.println();
        computeAndDisplayFastestTimes();
    }


}
