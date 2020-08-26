package org.fyan102.algorithms.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
        catch (IOException e) {
            e.printStackTrace();
        }
        return heuristic.toString();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String[] className = {"org.fyan102.algorithms.demo.NPuzzle"};
        primaryStage.setTitle("Algorithms Demo");
        final IStateRepresent[] init = {new NPuzzle(3, "2 8 3 1 6 4 7 B 5",
                new NPuzzle(3, "1 2 3 8 B 4 7 6 5", null))};
        final IGraphSearchSolver[] solver = {new AStar(init[0])};

        BorderPane border = new BorderPane();

        VBox vbLeft = new VBox();
        Label lProblem = new Label("Problems");
        ToggleGroup tgProblem = new ToggleGroup();
        RadioButton rbNPuzzle = new RadioButton("N Puzzle");
        rbNPuzzle.setSelected(true);
        RadioButton rbTravelingSalesMan = new RadioButton("Traveling Sales Man");
        rbNPuzzle.setToggleGroup(tgProblem);
        rbTravelingSalesMan.setToggleGroup(tgProblem);

        vbLeft.getChildren().addAll(lProblem, rbNPuzzle, rbTravelingSalesMan);
        vbLeft.setPadding(new Insets(15, 12, 15, 12));
        vbLeft.setStyle("-fx-background-color: #6699CC;");
        vbLeft.setSpacing(10);
        border.setLeft(vbLeft);

        Label lAlgorithm = new Label("\nAlgorithms");
        ToggleGroup tgAlgorithm = new ToggleGroup();
        RadioButton rbAStar = new RadioButton("A*");
        rbAStar.setSelected(true);
        rbAStar.setToggleGroup(tgAlgorithm);
        vbLeft.getChildren().addAll(lAlgorithm, rbAStar);

        TextArea lHeuristic = new TextArea("Heuristic\n============\n" +
                getHeuristic(className[0]));
        lHeuristic.setEditable(false);
        lHeuristic.setMinHeight(400);
        lHeuristic.setMaxWidth(400);
        Button btnHeuristic = new Button("New Heuristic");
        vbLeft.getChildren().addAll(btnHeuristic, lHeuristic);

        Button btnSolve = new Button("Solve");
        Button btnSolveStep = new Button("Solve step by step");
        Button btnClear = new Button("Clear");

        VBox vBox = new VBox();
        border.setCenter(vBox);

        HBox hbButtons = new HBox();
        hbButtons.getChildren().addAll(btnSolve, btnSolveStep, btnClear);
        hbButtons.setPadding(new Insets(15, 12, 15, 12));
        hbButtons.setSpacing(10);
        hbButtons.setStyle("-fx-background-color: #336699;");
        border.setTop(hbButtons);

        Scene scene = new Scene(border, 900, 700);
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
                    className[0] = "org.fyan102.algorithms.demo.NPuzzle";
                    lHeuristic.setText("Heuristic\n============\n" +
                            getHeuristic("org.fyan102.algorithms.demo.NPuzzle"));
                }
                else if (rb.getText().equals("Traveling Sales Man")) {
                    init[0] = new TravelingSalesMan("A, B, C, D, E",
                            "AB 7, AC 6, AD 10, AE 13, BC 7, BD 10, BE 10, CD 5, CE 9, DE 6");
                    solver[0] = new AStar(init[0]);
                    finished[0] = false;
                    className[0] = "org.fyan102.algorithms.demo.TravelingSalesMan";
                    lHeuristic.setText("Heuristic\n============\n" +
                            getHeuristic("org.fyan102.algorithms.demo.TravelingSalesMan"));

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
        btnHeuristic.setOnAction(actionEvent -> {
            HeuristicDialog aHeuristic = new HeuristicDialog(className[0]);
            aHeuristic.showAndWait();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
