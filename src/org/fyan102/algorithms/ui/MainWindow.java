package org.fyan102.algorithms.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.fyan102.algorithms.Interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.Interfaces.ISearchTree;
import org.fyan102.algorithms.Interfaces.IStateRepresent;
import org.fyan102.algorithms.algorithm.AStar;
import org.fyan102.algorithms.algorithm.Node;
import org.fyan102.algorithms.demo.TravelingSalesMan;

public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private TreeItem<String> getTreeItem(Node current) {
        TreeItem<String> treeItem = new TreeItem<>(current.getValue().toString());
        treeItem.setExpanded(true);
        for (Node child : current.getChildren()) {
            treeItem.getChildren().add(getTreeItem(child));
        }
        return treeItem;
    }

    private TreeView<String> getTreeView(ISearchTree<IStateRepresent> result) {
        TreeItem<String> tree = getTreeItem(result.getRoot());
        TreeView<String> treeView = new TreeView<>(tree);
        treeView.setMinHeight(600);
        return treeView;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Algorithms Demo");
//        NPuzzle goal = new NPuzzle(3, "1 2 3 8 B 4 7 6 5", null);
//        IStateRepresent init = new NPuzzle(3, "2 8 3 1 6 4 7 B 5", goal);
        IStateRepresent init = new TravelingSalesMan("A, B, C, D, E",
                "AB 7, AC 6, AD 10, AE 13, BC 7, BD 10, BE 10, CD 5, CE 9, DE 6");
        IGraphSearchSolver solver = new AStar(init);

        Button btnSolve = new Button("Solve");
        Button btnSolveStep = new Button("Solve step by step");
        Button btnClear = new Button("Clear");
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Scene scene = new Scene(vBox, 600, 600);
        scene.setFill(Color.LIGHTGRAY);
        final TreeView[] treeView = new TreeView[]{null};
        btnSolve.setOnAction(actionEvent -> {
            vBox.getChildren().remove(treeView[0]);
            solver.solve();
            treeView[0] = getTreeView(solver.getSearchTree());
            vBox.getChildren().add(treeView[0]);
        });
        final boolean[] finished = {false};
        btnClear.setOnAction(actionEvent -> {
            vBox.getChildren().remove(treeView[0]);
            treeView[0] = null;
            finished[0] = false;
        });
        btnSolveStep.setOnAction(actionEvent -> {
            if (!finished[0]) {
                if (treeView[0] == null) {
                    solver.reset();
                }
                vBox.getChildren().remove(treeView[0]);
                IStateRepresent state = solver.solveOneStep();
                finished[0] = state.isGoalState();
                treeView[0] = getTreeView(solver.getSearchTree());
                vBox.getChildren().add(treeView[0]);
            }
        });
        hBox.getChildren().addAll(btnSolve, btnSolveStep, btnClear);
        vBox.getChildren().add(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
