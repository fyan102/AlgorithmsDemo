package org.fyan102.algorithms.algorithm;

import org.fyan102.algorithms.interfaces.IStateRepresent;

import java.util.PriorityQueue;

public class Dijkstra extends AStar {
    /**
     * The constructor of Dijkstra class.
     * Initialize the open set and the closed set
     *
     * @param initState the initial state
     */
    public Dijkstra(IStateRepresent initState) {
        super(initState);
        setOpenSet(new PriorityQueue<>((iStateRepresent, t1) -> {
            double f1 = iStateRepresent.cost();
            double f2 = t1.cost();
            return Double.compare(f1, f2);
        }));
    }
}
