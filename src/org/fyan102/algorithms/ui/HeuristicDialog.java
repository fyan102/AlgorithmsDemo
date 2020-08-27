package org.fyan102.algorithms.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.fyan102.algorithms.util.HeuristicGetter;

public class HeuristicDialog extends Alert {
    public HeuristicDialog(String className) {
        super(AlertType.CONFIRMATION);
        setTitle("Edit Heuristic");
        setHeaderText(className);
        TextArea taHeuristic = new TextArea(new HeuristicGetter().getHeuristic(className));
        GridPane.setVgrow(taHeuristic, Priority.ALWAYS);
        GridPane.setHgrow(taHeuristic, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(taHeuristic, 0, 1);
        getDialogPane().setExpandableContent(expContent);
        getDialogPane().setExpanded(true);
    }
}
