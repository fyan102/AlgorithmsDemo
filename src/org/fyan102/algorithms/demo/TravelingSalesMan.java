package org.fyan102.algorithms.demo;

import org.fyan102.algorithms.interfaces.IAction;
import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TravelingSalesMan implements IStateRepresent {
    private String path;
    private String[] cities;
    private HashMap<String, Integer> distances;
    private int cost;
    private double heuristic;
    private TravelingSalesMan parent;

    /**
     * The constructor for Traveling Sales Man problem representation
     *
     * @param cityList A list of cities, separated by a comma
     * @param pathList A list of paths, all paths are separated by a comma, in each path,
     *                 firstly it should be two cities, followed by a space, then the
     *                 distance between these two cities.
     */
    public TravelingSalesMan(String cityList, String pathList) {
        String[] cities = cityList.split(",");
        this.cities = new String[cities.length];
        for (int i = 0; i < this.cities.length; i++) {
            this.cities[i] = cities[i].trim();
        }
        String[] paths = pathList.split(",");
        distances = new HashMap<>();
        for (String path : paths) {
            String[] p = path.trim().split(" ");
            distances.put(p[0], Integer.parseInt(p[1]));
            distances.put(p[0].substring(1) + p[0].substring(0, 1), Integer.parseInt(p[1]));
        }
        parent = null;
        path = this.cities[0];
        heuristic = heuristic();
        cost = 0;
    }

    /**
     * The second constructor for the traveling sales man problem
     *
     * @param path   the new path
     * @param parent the parent of the current node
     */
    private TravelingSalesMan(String path, TravelingSalesMan parent) {
        this.parent = parent;
        this.path = path;
        this.cities = parent.cities;
        this.distances = parent.distances;
        heuristic = heuristic();
        this.cost = parent.cost + distances.get(path.substring(path.length() - 2));
    }

    /**
     * The constraints method.
     *
     * @return always true
     */
    @Override
    public boolean constraints() {
        return true;
    }

    /**
     * The cost of current state
     *
     * @return the cost of current state
     */
    @Override
    public double cost() {
        return cost;
    }

    /**
     * Check whether two states are the same
     *
     * @param o the other state
     * @return true if the two states are the same, false otherwise
     */
    @Override
    public boolean equals(IStateRepresent o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelingSalesMan that = (TravelingSalesMan) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public IStateRepresent getParent() {
        return parent;
    }

    /**
     * The hashCode method
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    /**
     * The heuristic method
     *
     * @return the value of heuristic
     */
    @Override
    public double heuristic() {
        // HEURISTIC START
        heuristic = 0;
        if (path.length() != cities.length + 1) {
            ArrayList<String> start = new ArrayList<>();
            ArrayList<String> destination = new ArrayList<>();
            start.add(path.substring(path.length() - 1));
            destination.add(cities[0]);
            for (String city : cities) {
                if (!path.contains(city)) {
                    start.add(city);
                    destination.add(city);
                }
            }

            for (String s : start) {
                int minDistance = -1;
                for (String value : destination) {
                    Integer distance = distances.get(s + value);
                    if (distance != null) {
                        if (minDistance == -1 || minDistance > distance) {
                            minDistance = distance;
                        }
                    }
                }
                heuristic += minDistance;
            }
        }
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // Just some comments
        // HEURISTIC END
        return this.heuristic;
    }

    /**
     * Check whether the current state is the goal state
     *
     * @return true if the current state is the goal, false otherwise
     */
    @Override
    public boolean isGoalState() {
        return path.charAt(0) == path.charAt(path.length() - 1) &&
                path.length() == cities.length + 1;
    }

    /**
     * The operations method will return a list of possible operations
     *
     * @return a list of methods that represents a list of possible operations
     */
    @Override
    public ArrayList<IAction> operations() {
        ArrayList<IAction> IActions = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            if (distances.get(path.charAt(path.length() - 1) + cities[i]) != null &&
                    (path.length() == cities.length && i == 0 ||
                            path.length() != cities.length && !path.contains(cities[i]))) {
                int finalI = i;
                IActions.add(() -> new TravelingSalesMan(path + cities[finalI], this));
            }
        }
        return IActions;
    }

    /**
     * The toString method
     *
     * @return a String represents the current state
     */
    @Override
    public String toString() {
        return path + '\n' +
                "g = " + cost +
                ", h = " + heuristic +
                ", f = " + (cost + heuristic) +
                '\n';
    }
}
