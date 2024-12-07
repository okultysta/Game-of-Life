package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class IntroSceneController {

    @FXML
    private Text chooseFillAmountTitle;
    @FXML
    private Text xLabel;
    @FXML
    private Text yLabel;
    @FXML
    private TextField xValue;
    @FXML
    private TextField yValue;
    @FXML
    private Text title;
    @FXML
    private Text dimChoose;
    @FXML
    private ChoiceBox fillChoosing;
    @FXML
    private Button startButton;


    public IntroSceneController() {
        System.out.println("IntroSceneController");
    }

    @FXML
    public void initialize() {
        title.setText("Welcome to the Game of Life!");
        dimChoose.setText("Choose the board dimensions (both between 2 and 20):");
        xLabel.setText("X: ");
        yLabel.setText("Y: ");

    }

    public void startSimulation(ActionEvent actionEvent) {

    }

}
