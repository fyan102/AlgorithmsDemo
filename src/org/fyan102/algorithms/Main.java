package org.fyan102.algorithms;

import org.fyan102.algorithms.demos.NPuzzle;
import org.fyan102.algorithms.ui.ClassGenerator;

public class Main {
    public static void main(String[] args) {
        NPuzzle init = new NPuzzle(3, "2 8 3 1 6 4 7 B 5");
        NPuzzle goal = new NPuzzle(3, "1 2 3 8 B 4 7 6 5");
        System.out.println(new AStar().solve(init, goal));
    }
}
