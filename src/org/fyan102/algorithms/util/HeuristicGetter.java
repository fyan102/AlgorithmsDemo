package org.fyan102.algorithms.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The HeuristicGetter class is used for getting the heuristic from one class
 *
 * @author Fan
 * @version 1.0
 */
public class HeuristicGetter {
    /**
     * getHeuristic() method is used for getting the code for the heuristic from one class
     *
     * @param className the full class name (include the packages)
     * @return the code for heuristic function.
     */
    public String getHeuristic(String className) {
        StringBuilder heuristic = new StringBuilder();
        try {
            FileReader reader = new FileReader("src/" +
                    className.replace(".", "/") + ".java");
            try (reader; Scanner scanner = new Scanner(reader)) {
                boolean isHeuristic = false;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("HEURISTIC START")) {
                        isHeuristic = true;
                        continue;
                    } else if (line.contains("HEURISTIC END")) {
                        break;
                    }
                    if (isHeuristic) {
                        heuristic.append(line).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return heuristic.toString();
    }
}
