package org.fyan102.algorithms.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class HeuristicDialog extends Alert {
    public HeuristicDialog(String className) {
        super(AlertType.CONFIRMATION);
        setTitle("Edit Heuristic");
        setHeaderText(className);
        TextArea taHeuristic = new TextArea(getHeuristic(className));
        GridPane.setVgrow(taHeuristic, Priority.ALWAYS);
        GridPane.setHgrow(taHeuristic, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(taHeuristic, 0, 1);
        getDialogPane().setExpandableContent(expContent);
        getDialogPane().setExpanded(true);
    }

    private String getHeuristic(String className) {
        StringBuilder heuristic = new StringBuilder();
        try {
            FileReader reader = new FileReader("src/" +
                    className.replace(".", "/") + ".java");
            Scanner scanner = new Scanner(reader);
            try {
                boolean isHeuristic = false;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("HEURISTIC START")) {
                        isHeuristic = true;
                        continue;
                    }
                    else if (line.contains("HEURISTIC END")) {
                        break;
                    }
                    if (isHeuristic) {
                        heuristic.append(line).append("\n");
                    }
                }
            }
            finally {
                scanner.close();
                reader.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return heuristic.toString();
    }
}
