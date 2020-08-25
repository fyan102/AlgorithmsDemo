package org.fyan102.algorithms.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.fyan102.algorithms.algorithm.AStar;
import org.fyan102.algorithms.algorithm.Node;
import org.fyan102.algorithms.demo.NPuzzle;
import org.fyan102.algorithms.demo.TravelingSalesMan;
import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.ISearchTree;
import org.fyan102.algorithms.interfaces.IStateRepresent;

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
        final IStateRepresent[] init = {new NPuzzle(3, "2 8 3 1 6 4 7 B 5",
                new NPuzzle(3, "1 2 3 8 B 4 7 6 5", null))};
        final IGraphSearchSolver[] solver = {new AStar(init[0])};
        TilePane pane = new TilePane();
        Label lProblem = new Label("Problems");
        ToggleGroup tgProblem = new ToggleGroup();
        RadioButton rbNPuzzle = new RadioButton("N Puzzle");
        rbNPuzzle.setSelected(true);
        RadioButton rbTravelingSalesMan = new RadioButton("Traveling Sales Man");
        rbNPuzzle.setToggleGroup(tgProblem);
        rbTravelingSalesMan.setToggleGroup(tgProblem);
        pane.getChildren().addAll(lProblem, rbNPuzzle, rbTravelingSalesMan);

        Button btnSolve = new Button("Solve");
        Button btnSolveStep = new Button("Solve step by step");
        Button btnClear = new Button("Clear");
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Scene scene = new Scene(vBox, 900, 700);
        scene.setFill(Color.LIGHTGRAY);
        final TreeView[] treeView = new TreeView[]{null};
        final boolean[] finished = {false};
        tgProblem.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton rb = (RadioButton) tgProblem.getSelectedToggle();
            if (rb != null) {
                if (rb.getText().equals("N Puzzle")) {
                    init[0] = new NPuzzle(3, "2 8 3 1 6 4 7 B 5",
                            new NPuzzle(3, "1 2 3 8 B 4 7 6 5", null));
                    solver[0] = new AStar(init[0]);
                    finished[0] = false;
                }
                else if (rb.getText().equals("Traveling Sales Man")) {
                    init[0] = new TravelingSalesMan("A, B, C, D, E",
                            "AB 7, AC 6, AD 10, AE 13, BC 7, BD 10, BE 10, CD 5, CE 9, DE 6");
                    solver[0] = new AStar(init[0]);
                    finished[0] = false;
                }
            }
        });
        btnSolve.setOnAction(actionEvent -> {
            vBox.getChildren().remove(treeView[0]);
            solver[0].solve();
            treeView[0] = getTreeView(solver[0].getSearchTree());
            vBox.getChildren().add(treeView[0]);
            finished[0] = true;
        });

        btnClear.setOnAction(actionEvent -> {
            vBox.getChildren().remove(treeView[0]);
            treeView[0] = null;
            finished[0] = false;
        });
        btnSolveStep.setOnAction(actionEvent -> {
            if (!finished[0]) {
                if (treeView[0] == null) {
                    solver[0].reset();
                }
                vBox.getChildren().remove(treeView[0]);
                IStateRepresent state = solver[0].solveOneStep();
                finished[0] = state.isGoalState();
                treeView[0] = getTreeView(solver[0].getSearchTree());
                vBox.getChildren().add(treeView[0]);
            }
        });
        hBox.getChildren().addAll(rbNPuzzle, rbTravelingSalesMan, btnSolve, btnSolveStep, btnClear);
        vBox.getChildren().addAll(pane, hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
