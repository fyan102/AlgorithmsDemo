package org.fyan102.algorithms.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File("src\\org\\fyan102\\algorithms\\ui\\MainWindow.fxml").toURI().toURL());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        VBox vbox = null;
        try {
            vbox = loader.<VBox>load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
}
