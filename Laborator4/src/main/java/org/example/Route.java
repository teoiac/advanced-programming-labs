package org.example;

import org.graph4j.shortestpath.DijkstraShortestPathDefault;
import java.util.*;
import org.graph4j.util.Path;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;

public class Route {
    private Graph graph;
    private Map<String, Integer> locationIndexes;
    private Map<Integer, String> indexToLocation;

    public Route(List<Location> locations) {
        graph = GraphBuilder.numVertices(locations.size()).buildGraph();
        locationIndexes = new HashMap<>();
        indexToLocation = new HashMap<>();


        int i = 0;
        for (Location loc : locations) {
            locationIndexes.put(loc.getName(), i);
            indexToLocation.put(i, loc.getName());
            i++;
        }
    }


    public void addPath(String from, String to, int travelTime) {
        Integer fromIndex = locationIndexes.get(from);
        Integer toIndex = locationIndexes.get(to);
        if (fromIndex != null && toIndex != null) {
            graph.addEdge(fromIndex, toIndex, travelTime);
        } else {
            System.out.println("Location not found");;
        }
    }

    public List<String> getShortestPath(String start, String destination) {
        Integer startIndex = locationIndexes.get(start);
        Integer destinationIndex = locationIndexes.get(destination);

        if (startIndex == null || destinationIndex == null) {
            System.out.println("Start or Destination not found");
            return null;
        }

        DijkstraShortestPathDefault dijkstra = new DijkstraShortestPathDefault(graph, startIndex);
        Path path = dijkstra.findPath(destinationIndex);

        List<String> pathNames = new ArrayList<>();
        for (int index : path.vertices()) {
            pathNames.add(indexToLocation.get(index));
        }
        return pathNames;
    }

    public Graph getGraph() {
        return graph;
    }

    public int getLocationIndex(String location) {
        return locationIndexes.get(location);
    }

    public double getTravelTime(String from, String to) {
        Integer fromIndex = getLocationIndex(from);
        Integer toIndex = getLocationIndex(to);
        if (fromIndex == null || toIndex == null) {
            return 1;
        }
        return graph.getEdgeWeight(fromIndex, toIndex);
    }
}
