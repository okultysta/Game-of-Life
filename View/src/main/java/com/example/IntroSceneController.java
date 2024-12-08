package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.FillOption.High;
import static com.example.FillOption.Low;
import static com.example.FillOption.Medium;


public class IntroSceneController {

    @FXML
    private Text chooseFillAmountTitle;
    @FXML
    private Text xabel;
    @FXML
    private Text ylabel;
    @FXML
    private TextField xvalue;
    @FXML
    private TextField yvalue;
    @FXML
    private Text title;
    @FXML
    private Text dimChoose;
    @FXML
    private ChoiceBox<FillOption> fillChoosing;
    @FXML
    private Button startButton;
    private BoardInformation boardInfo;


    public IntroSceneController() {
        System.out.println("IntroSceneController");
        boardInfo = new BoardInformation();
    }

    @FXML
    public void initialize() {
        title.setText("Welcome to the Game of Life!");
        dimChoose.setText("Choose the board dimensions (both between 2 and 20):");
        xabel.setText("X: ");
        ylabel.setText("Y: ");
        FillOption[] fillOptions = {Low, Medium, High};
        fillChoosing.getItems().addAll(fillOptions);
    }

    public void startSimulation(ActionEvent actionEvent) throws IOException {
        if (xvalue.getText().isEmpty() || yvalue.getText().isEmpty()) {
            showAlert("Enter value in dimension(s)!", true);
            return;
        }
        try {
            int a = Integer.parseInt(xvalue.getText());
            int b = Integer.parseInt(yvalue.getText());
        } catch (NumberFormatException apud) {
            showAlert("Enter a valid number!", true);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) <= 0 || Integer.parseInt(yvalue.getText()) <= 0) {
            showAlert("Enter a valid number!", true);
            return;
        }
        if (fillChoosing.getSelectionModel().getSelectedItem() == null) {
            showAlert("Choose the board filling!", false);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) > 20 || Integer.parseInt(yvalue.getText()) > 20) {
            showAlert("Dimensions must be not greater than 20", true);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) < 2 || Integer.parseInt(yvalue.getText()) < 2) {
            showAlert("Dimensions must not be lower than 2", true);
            return;
        }
        boardInfo.setFillPercentage(fillChoosing.getSelectionModel().getSelectedItem().getValue());
        boardInfo.setRow(Integer.parseInt(xvalue.getText()));
        boardInfo.setCol(Integer.parseInt(yvalue.getText()));
        Stage stage = (Stage) startButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent mainRoot = loader.load();
        Scene mainScene = new Scene(mainRoot);

        MainSceneController mainController = loader.getController();
        stage.setScene(mainScene);
        mainController.initializeBoard(boardInfo);
        stage.show();

    }


    public BoardInformation getBoardInfo() {
        return boardInfo;
    }

    private void showAlert(String message, boolean dim) {
        if (dim) {
            xvalue.setText("");
            yvalue.setText("");
            fillChoosing.getSelectionModel().clearSelection();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
