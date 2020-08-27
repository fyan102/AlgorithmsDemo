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
import org.fyan102.algorithms.algorithm.BreadthFirstSearch;
import org.fyan102.algorithms.algorithm.DepthFirstSearch;
import org.fyan102.algorithms.algorithm.Dijkstra;
import org.fyan102.algorithms.data_structure.Node;
import org.fyan102.algorithms.interfaces.IGraphSearchSolver;
import org.fyan102.algorithms.interfaces.ISearchTree;
import org.fyan102.algorithms.interfaces.IStateRepresent;
import org.fyan102.algorithms.util.ClassReLoader;
import org.fyan102.algorithms.util.HeuristicGetter;

import java.lang.reflect.InvocationTargetException;

/**
 * The main window of the application
 *
 * @author Fan
 * @version 1.0
 */
public class MainWindow extends Application {
    /**
     * The main() method
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * this method will generate a sample N Puzzle
     *
     * @param klass the class
     * @return the initial state
     */
    private IStateRepresent generateSampleNPuzzle(Class<?> klass) {
        try {
            IStateRepresent goal = (IStateRepresent) klass.getConstructor(
                    new Class[]{Integer.class, String.class, IStateRepresent.class}).
                    newInstance(new Object[]{3, "1 2 3 8 B 4 7 6 5", null});
            return (IStateRepresent) klass.getConstructor(
                    new Class[]{Integer.class, String.class, IStateRepresent.class}).
                    newInstance(new Object[]{3, "2 8 3 1 6 4 7 B 5", goal});
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * generate a sample traveling salesman problem
     *
     * @param klass the class
     * @return the the state
     */
    private IStateRepresent generateSampleTsm(Class<?> klass) {
        try {
            return (IStateRepresent) klass.getConstructor(
                    new Class[]{String.class, String.class}).
                    newInstance(new Object[]{"A, B, C, D, E",
                            "AB 7, AC 6, AD 10, AE 13, BC 7, BD 10, BE 10, CD 5, CE 9, DE 6"});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * The getTreeItem() method convert the result search tree to a tree item
     *
     * @param current the current node
     * @return the result tree item
     */
    private TreeItem<String> getTreeItem(Node current) {
        TreeItem<String> treeItem = new TreeItem<>(current.getValue().toString());
        treeItem.setExpanded(true);
        for (Node child : current.getChildren()) {
            treeItem.getChildren().add(getTreeItem(child));
        }
        return treeItem;
    }
    
    /**
     * get the tree view of the result
     *
     * @param result the result of the searching algorithm
     * @return the tree view
     */
    private TreeView<String> getTreeView(ISearchTree<IStateRepresent> result) {
        TreeItem<String> tree = getTreeItem(result.getRoot());
        TreeView<String> treeView = new TreeView<>(tree);
        treeView.setMinHeight(600);
        return treeView;
    }
    
    /**
     * start the ui
     *
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) {
        String classPath = "out/production/AlgorithmsDemo/";
        primaryStage.setTitle("Algorithms Demo");
        ClassReLoader loader = new ClassReLoader(classPath);
        var variables = new Object() {
            TreeView<String> treeView = new TreeView<>();
            boolean finished = false;
            //            boolean showHeuristic = true;
//            boolean showDepth = false;
            String className = "org.fyan102.algorithms.demo.NPuzzle";
            Class<?> problemClass = loader.findClass(className);
            IStateRepresent init = generateSampleNPuzzle(problemClass);
            IGraphSearchSolver solver = new AStar(init);
        };
    
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
    
        Label lAlgorithm = new Label("Algorithms");
        ToggleGroup tgAlgorithm = new ToggleGroup();
        RadioButton rbAStar = new RadioButton("A*");
        RadioButton rbBFS = new RadioButton("BFS");
        RadioButton rbDFS = new RadioButton("DFS");
        RadioButton rbDijkstra = new RadioButton("Dijkstra");
        rbAStar.setSelected(true);
        rbAStar.setToggleGroup(tgAlgorithm);
        rbBFS.setToggleGroup(tgAlgorithm);
        rbDFS.setToggleGroup(tgAlgorithm);
        rbDijkstra.setToggleGroup(tgAlgorithm);
        TextField tfMaximumDepth = new TextField();
        vbLeft.getChildren().addAll(lAlgorithm, rbAStar, rbBFS, rbDFS, rbDijkstra, tfMaximumDepth);
    
        TextArea lHeuristic = new TextArea("Heuristic\n============\n" +
                new HeuristicGetter().getHeuristic(variables.className));
        lHeuristic.setEditable(false);
        lHeuristic.setMinHeight(300);
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
        tfMaximumDepth.textProperty().addListener((observableValue, s, t1) -> {
            if (variables.solver instanceof DepthFirstSearch) {
                int maxDepth = 2;
                try {
                    maxDepth = Integer.parseInt(tfMaximumDepth.getText());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "The depth should be an integer").showAndWait();
                }
                ((DepthFirstSearch) variables.solver).setMaximumDepth(maxDepth);
            }
        });
        tgProblem.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton rb = (RadioButton) tgProblem.getSelectedToggle();
            if (rb != null) {
                if (rb.getText().equals("N Puzzle")) {
                    variables.className = "org.fyan102.algorithms.demo.NPuzzle";
                    variables.problemClass = loader.findClass(variables.className);
                    variables.init = generateSampleNPuzzle(variables.problemClass);
                    variables.solver.setInitState(variables.init);
                    variables.finished = false;
                    lHeuristic.setText("Heuristic\n============\n" +
                            new HeuristicGetter().getHeuristic("org.fyan102.algorithms.demo.NPuzzle"));
                } else if (rb.getText().equals("Traveling Sales Man")) {
                    variables.className = "org.fyan102.algorithms.demo.TravelingSalesMan";
                    variables.problemClass = loader.findClass(variables.className);
                    variables.init = generateSampleTsm(variables.problemClass);
                    variables.solver.setInitState(variables.init);
                    variables.finished = false;
                    lHeuristic.setText("Heuristic\n============\n" +
                            new HeuristicGetter().getHeuristic(
                                    "org.fyan102.algorithms.demo.TravelingSalesMan"));
                }
            }
        });
        tgAlgorithm.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton rb = (RadioButton) tgAlgorithm.getSelectedToggle();
            if (rb != null) {
                switch (rb.getText()) {
                    case "A*":
                        variables.solver = new AStar(variables.init);
                        break;
                    case "BFS":
                        variables.solver = new BreadthFirstSearch(variables.init);
                        break;
                    case "DFS":
                        int depth = 2;
                        try {
                            depth = Integer.parseInt(tfMaximumDepth.getText());
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR, "The depth should be an integer").showAndWait();
                        }
                        variables.solver = new DepthFirstSearch(variables.init, depth);
                        break;
                    case "Dijkstra":
                        variables.solver = new Dijkstra(variables.init);
                        break;
                }
                variables.finished = false;
            }
        });
        btnSolve.setOnAction(actionEvent -> {
            vBox.getChildren().remove(variables.treeView);
            variables.solver.solve();
            variables.treeView = getTreeView(variables.solver.getSearchTree());
            vBox.getChildren().add(variables.treeView);
            variables.finished = true;
        });
        btnClear.setOnAction(actionEvent -> {
            vBox.getChildren().remove(variables.treeView);
            variables.treeView = null;
            variables.finished = false;
        });
        btnSolveStep.setOnAction(actionEvent -> {
            if (!variables.finished) {
                if (variables.treeView == null) {
                    variables.solver.reset();
                }
                vBox.getChildren().remove(variables.treeView);
                IStateRepresent state = variables.solver.solveOneStep();
                variables.finished = state.isGoalState();
                variables.treeView = getTreeView(variables.solver.getSearchTree());
                vBox.getChildren().add(variables.treeView);
            }
        });
        btnHeuristic.setOnAction(actionEvent -> {
            HeuristicDialog aHeuristic = new HeuristicDialog(variables.className);
            aHeuristic.showAndWait();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
