package org.fyan102.algorithms.demos;

import org.fyan102.algorithms.Action;
import org.fyan102.algorithms.IStateRepresent;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TravellingSalesMan implements IStateRepresent {
    private String path;
    private String[] cities;
    private HashMap<String, Integer> distances;
    private int cost;
    private double heuristic;
    private TravellingSalesMan parent;

    public TravellingSalesMan(String cityList, String pathList) {
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
        heuristic = heuristics();
        cost = 0;
    }

    public TravellingSalesMan(String path, TravellingSalesMan parent) {
        this.parent = parent;
        this.path = path;
        this.cities = parent.cities;
        this.distances = parent.distances;
        heuristic = heuristics();
        this.cost = parent.cost + distances.get(path.substring(path.length() - 2));
    }

    @Override
    public boolean isGoalState() {
        return path.charAt(0) == path.charAt(path.length() - 1) &&
                path.length() == cities.length + 1;
    }

    @Override
    public ArrayList<Action> operations() {
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            if (distances.get(path.charAt(path.length() - 1) + cities[i]) != null &&
                    (path.length() == cities.length && i == 0 ||
                            path.length() != cities.length && !path.contains(cities[i]))) {
                int finalI = i;
                actions.add(() -> new TravellingSalesMan(path + cities[finalI], this));
            }
        }
        return actions;
    }

    @Override
    public boolean constraints() {
        return true;
    }

    @Override
    public double heuristics() {
        // HEURISTIC START
//        heuristic = 0;
//        if (path.length() != cities.length + 1) {
//            for (int i = 0; i < cities.length; i++) {
//                if (!path.contains(cities[i]) || i == 0) {
//                    int minDistance = -1;
//                    for (int j = 0; j < cities.length; j++) {
//                        Integer distance = distances.get(cities[i] + cities[j]);
//                        if (distance != null) {
//                            if (minDistance == -1 || minDistance > distance) {
//                                minDistance = distance;
//                            }
//                        }
//                    }
//                    heuristic += minDistance;
//                }
//            }
//        }
        int h = 0;
        if (path.length() != cities.length + 1) {
            ArrayList<String> start = new ArrayList<>();
            ArrayList<String> destination = new ArrayList<>();
            start.add(path.substring(path.length() - 1));
            destination.add(cities[0]);
            for (int i = 0; i < cities.length; i++) {
                if (!path.contains(cities[i])) {
                    start.add(cities[i]);
                    destination.add(cities[i]);
                }
            }

            for (int i = 0; i < start.size(); i++) {
                int minDistance = -1;
                for (int j = 0; j < destination.size(); j++) {
                    Integer distance = distances.get(start.get(i) + destination.get(j));
                    if (distance != null) {
                        if (minDistance == -1 || minDistance > distance) {
                            minDistance = distance;
                        }
                    }
                }
                h += minDistance;
            }
        }
        heuristic = h;
        return heuristic;
        // HEURISTIC END
    }

    @Override
    public double cost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravellingSalesMan that = (TravellingSalesMan) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "TravellingSalesMan{" +
                "path='" + path + '\'' +
                ", cost=" + cost +
                ", heuristic=" + heuristic +
                '}';
    }
}
