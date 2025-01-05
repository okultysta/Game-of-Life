package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("IntroScene.fxml"));
        AnchorPane root = null;
        Logger logger = LoggerFactory.getLogger(MainApplication.class);
        try {
            root = loader.load();
        } catch(IOException ioe) {
            throw new InvalidSceneFileException("Invalid scene file!", ioe);
        }  catch (InvalidSceneFileException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game Of Life Intro");
        primaryStage.show();
    }
}

