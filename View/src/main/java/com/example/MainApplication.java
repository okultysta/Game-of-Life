package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.example.IntroSceneController;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("IntroScene.fxml"));

        AnchorPane root = loader.load();


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        primaryStage.setTitle("Game Of Life Intro");



        primaryStage.show();
    }
}

