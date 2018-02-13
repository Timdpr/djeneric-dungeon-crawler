package main.java.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Creates and displays the GUI for Djeneric Dungeon Crawler!
 * 
 * @version 2.0
 * @author tp275
 */
public class Main extends Application {
    
    /**
     * Sets up the application by loading the FXML and setting the title and icons, then shows it
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/java/gui/GUI.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/main/java/gui/design.css").toExternalForm());
            primaryStage.setTitle("Djeneric Dungeon Crawler");
            primaryStage.getIcons().addAll(
                    // JavaFX is bad at auto choosing icons, so, many are given:
                    new Image(getClass().getResourceAsStream("/main/res/sword16.png")), 
                    new Image(getClass().getResourceAsStream("/main/res/sword32.png")),
                    new Image(getClass().getResourceAsStream("/main/res/sword48.png")),
                    new Image(getClass().getResourceAsStream("/main/res/sword64.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch(IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the application using the built-in JavaFx launch method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
