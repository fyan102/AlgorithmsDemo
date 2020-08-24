package org.fyan102.algorithms;

import org.fyan102.algorithms.Interfaces.IGraphSearch;
import org.fyan102.algorithms.algorithm.AStar;
import org.fyan102.algorithms.demo.NPuzzle;
import org.fyan102.algorithms.demo.TravelingSalesMan;

public class Main {
    public static void main(String[] args) {
        NPuzzle goal = new NPuzzle(3, "1 2 3 8 B 4 7 6 5", null);
        NPuzzle init = new NPuzzle(3, "2 8 3 1 6 4 7 B 5", goal);
        IGraphSearch search = new AStar(init);
        search.solve();
        System.out.println(search.getSearchTree());
        TravelingSalesMan tsm = new TravelingSalesMan("A, B, C, D, E",
                "AB 7, AC 6, AD 10, AE 13, BC 7, BD 10, BE 10, CD 5, CE 9, DE 6");
        IGraphSearch search2 = new AStar(tsm);
        search2.solve();
        System.out.println(search.getSearchTree());
    }
}
