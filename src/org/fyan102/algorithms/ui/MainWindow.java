package org.fyan102.algorithms.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import java.util.HashMap;
import java.util.Map;

/**
 * The main window of the application
 *
 * @author Fan
 * @version 1.0
 */
public class MainWindow extends Application {
    // Theme definitions
    private static class Theme {
        final String background;
        final String accent;
        final String text;
        final String button;
        final String hover;

        Theme(String background, String accent, String text, String button, String hover) {
            this.background = background;
            this.accent = accent;
            this.text = text;
            this.button = button;
            this.hover = hover;
        }
    }

    private static final Map<String, Theme> THEMES = new HashMap<>();
    static {
        // Light Theme - Soft white background with subtle gray accents
        THEMES.put("Light", new Theme(
            "#F8F9FA",  // Soft white background
            "#E9ECEF",  // Light gray accent
            "#1A1A1A",  // Almost black text for better contrast
            "#6C757D",  // Medium gray buttons
            "#495057"   // Darker gray hover
        ));

        // Dark Theme - Deep dark background with subtle highlights
        THEMES.put("Dark", new Theme(
            "#212529",  // Dark background
            "#343A40",  // Slightly lighter dark accent
            "#FFFFFF",  // Pure white text for maximum contrast
            "#495057",  // Medium gray buttons
            "#6C757D"   // Lighter gray hover
        ));

        // Blue Theme - Professional blue tones
        THEMES.put("Blue", new Theme(
            "#E7F5FF",  // Very light blue background
            "#D0EBFF",  // Light blue accent
            "#0A3D62",  // Deep navy text for better readability
            "#339AF0",  // Medium blue buttons
            "#228BE6"   // Slightly darker blue hover
        ));

        // Pink Theme - Soft pink tones
        THEMES.put("Pink", new Theme(
            "#FFF0F6",  // Very light pink background
            "#FFE3F3",  // Light pink accent
            "#862E9C",  // Deep purple text for better contrast
            "#E64980",  // Medium pink buttons
            "#D6336C"   // Slightly darker pink hover
        ));
    }

    private String currentTheme = "Light";  // Changed default theme to Light
    private Theme theme = THEMES.get(currentTheme);
    private BorderPane border;
    private VBox vbLeft;
    private HBox hbButtons;
    private VBox vBox;

    // Modern Dark Theme with Purple Accents
    private static final String BACKGROUND_COLOR = "#1A1A2E";  // Deep navy
    private static final String ACCENT_COLOR = "#6C63FF";      // Bright purple
    private static final String TEXT_COLOR = "#E6E6E6";        // Soft white
    private static final String BUTTON_COLOR = "#4A4AFF";      // Royal blue
    private static final String HOVER_COLOR = "#8B7FFF";       // Light purple

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
        treeView.setStyle("-fx-background-color: " + BACKGROUND_COLOR + "; " +
                         "-fx-text-fill: " + TEXT_COLOR + "; " +
                         "-fx-font-size: 14px;");
        return treeView;
    }
    
    private void applyTheme() {
        theme = THEMES.get(currentTheme);
        if (border != null) {
            border.setStyle("-fx-background-color: " + theme.background + ";");
        }
        if (vbLeft != null) {
            vbLeft.setStyle("-fx-background-color: " + theme.accent + "; " +
                           "-fx-background-radius: 10; " +
                           "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        }
        if (vBox != null) {
            vBox.setStyle("-fx-background-color: " + theme.background + ";");
        }
    }
    
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + theme.button + "; " +
                       "-fx-text-fill: " + theme.text + "; " +
                       "-fx-font-size: 14px; " +
                       "-fx-padding: 10 20; " +
                       "-fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + theme.hover + "; " +
                                                    "-fx-text-fill: " + theme.text + "; " +
                                                    "-fx-font-size: 14px; " +
                                                    "-fx-padding: 10 20; " +
                                                    "-fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + theme.button + "; " +
                                                   "-fx-text-fill: " + theme.text + "; " +
                                                   "-fx-font-size: 14px; " +
                                                   "-fx-padding: 10 20; " +
                                                   "-fx-background-radius: 5;"));
        return button;
    }
    
    private RadioButton createStyledRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-text-fill: " + theme.text + "; " +
                           "-fx-font-size: 14px;");
        return radioButton;
    }
    
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: " + theme.text + "; " +
                      "-fx-font-size: 16px; " +
                      "-fx-font-weight: bold;");
        return label;
    }
    
    /**
     * start the ui
     *
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) {
        String classPath = "build/classes/java/main/";
        primaryStage.setTitle("SearchVista");
        
        // Set the program icon
        Group icon = ProgramIcon.createIcon(32);
        primaryStage.getIcons().add(icon.snapshot(null, null));
        
        ClassReLoader loader = new ClassReLoader(classPath);
        var variables = new Object() {
            TreeView<String> treeView = new TreeView<>();
            boolean finished = false;
            String className = "org.fyan102.algorithms.demo.NPuzzle";
            Class<?> problemClass = loader.findClass(className);
            IStateRepresent init = generateSampleNPuzzle(problemClass);
            IGraphSearchSolver solver = new AStar(init);
        };

        // Initialize the main container
        border = new BorderPane();
        
        // Create menu bar with improved styling
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: " + theme.accent + "; " +
                        "-fx-padding: 5;");
        
        Menu themeMenu = new Menu("Theme");
        themeMenu.setStyle("-fx-text-fill: " + theme.text + "; " +
                          "-fx-font-size: 14px;");
        
        ToggleGroup themeGroup = new ToggleGroup();
        
        for (String themeName : THEMES.keySet()) {
            RadioMenuItem themeItem = new RadioMenuItem(themeName);
            themeItem.setStyle("-fx-text-fill: " + theme.text + "; " +
                             "-fx-font-size: 14px;");
            themeItem.setToggleGroup(themeGroup);
            themeItem.setSelected(themeName.equals(currentTheme));
            themeItem.setOnAction(e -> {
                currentTheme = themeName;
                applyTheme();
                // Update menu bar style after theme change
                menuBar.setStyle("-fx-background-color: " + theme.accent + "; " +
                               "-fx-padding: 5;");
                themeMenu.setStyle("-fx-text-fill: " + theme.text + "; " +
                                 "-fx-font-size: 14px;");
                for (MenuItem item : themeMenu.getItems()) {
                    item.setStyle("-fx-text-fill: " + theme.text + "; " +
                                "-fx-font-size: 14px;");
                }
            });
            themeMenu.getItems().add(themeItem);
        }
        
        menuBar.getMenus().add(themeMenu);
        
        // Create a container for the menu bar to ensure it's visible
        VBox topContainer = new VBox();
        topContainer.getChildren().add(menuBar);
        border.setTop(topContainer);

        // Initialize all UI components
        vbLeft = new VBox(15);
        vbLeft.setPadding(new Insets(20));

        Label lProblem = createStyledLabel("Problems");
        ToggleGroup tgProblem = new ToggleGroup();
        RadioButton rbNPuzzle = createStyledRadioButton("N Puzzle");
        RadioButton rbTravelingSalesMan = createStyledRadioButton("Traveling Sales Man");
        rbNPuzzle.setSelected(true);
        rbNPuzzle.setToggleGroup(tgProblem);
        rbTravelingSalesMan.setToggleGroup(tgProblem);

        Label lAlgorithm = createStyledLabel("Algorithms");
        ToggleGroup tgAlgorithm = new ToggleGroup();
        RadioButton rbAStar = createStyledRadioButton("A*");
        RadioButton rbBFS = createStyledRadioButton("BFS");
        RadioButton rbDFS = createStyledRadioButton("DFS");
        RadioButton rbDijkstra = createStyledRadioButton("Dijkstra");
        rbAStar.setSelected(true);
        rbAStar.setToggleGroup(tgAlgorithm);
        rbBFS.setToggleGroup(tgAlgorithm);
        rbDFS.setToggleGroup(tgAlgorithm);
        rbDijkstra.setToggleGroup(tgAlgorithm);

        TextField tfMaximumDepth = new TextField();
        tfMaximumDepth.setPromptText("Maximum Depth");
        tfMaximumDepth.setStyle("-fx-background-color: " + theme.text + "; " +
                               "-fx-text-fill: " + theme.background + "; " +
                               "-fx-font-size: 14px; " +
                               "-fx-padding: 8; " +
                               "-fx-background-radius: 5;");

        TextArea lHeuristic = new TextArea("Heuristic\n============\n" +
                new HeuristicGetter().getHeuristic(variables.className));
        lHeuristic.setEditable(false);
        lHeuristic.setMinHeight(300);
        lHeuristic.setMaxWidth(400);
        lHeuristic.setStyle("-fx-background-color: " + theme.accent + "; " +
                           "-fx-text-fill: " + theme.text + "; " +
                           "-fx-font-size: 14px; " +
                           "-fx-background-radius: 5;");

        Button btnHeuristic = createStyledButton("New Heuristic");
        vbLeft.getChildren().addAll(lProblem, rbNPuzzle, rbTravelingSalesMan, 
                                  new Separator(), lAlgorithm, rbAStar, rbBFS, rbDFS, rbDijkstra,
                                  tfMaximumDepth, new Separator(), btnHeuristic, lHeuristic);
        border.setLeft(vbLeft);

        // Center Panel
        vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        border.setCenter(vBox);

        // Move the buttons to a separate container below the menu bar
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.setStyle("-fx-background-color: " + theme.accent + "; " +
                               "-fx-background-radius: 10; " +
                               "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        Button btnSolve = createStyledButton("Solve");
        Button btnSolveStep = createStyledButton("Solve Step by Step");
        Button btnClear = createStyledButton("Clear");
        buttonContainer.getChildren().addAll(btnSolve, btnSolveStep, btnClear);
        
        // Add the button container below the menu bar
        topContainer.getChildren().add(buttonContainer);

        // Apply theme after all components are initialized
        applyTheme();

        Scene scene = new Scene(border, 1000, 800);
        scene.setFill(Color.web(theme.background));
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
