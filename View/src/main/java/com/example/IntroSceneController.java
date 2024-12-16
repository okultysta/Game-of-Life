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
import java.util.*;

import static com.example.FillOption.High;
import static com.example.FillOption.Low;
import static com.example.FillOption.Medium;



public class IntroSceneController {
    @FXML
    private Button langConfirmIntro;
    @FXML
    private Text langChangeTitleIntro;
    @FXML
    private ChoiceBox<Language> langChooseIntro;
    @FXML
    private Text chooseFillAmountTitle;
    @FXML
    private Text xlabel;
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
    private TreeMap<String, String> errorMessages;


    public IntroSceneController() {
        System.out.println("IntroSceneController");
        boardInfo = new BoardInformation();
        errorMessages = new TreeMap<>();
        errorMessages.put("noBoardFilling", "");
        errorMessages.put("tooHighDim", "");
        errorMessages.put("tooLowDim", "");
        errorMessages.put("noDim", "");
        errorMessages.put("noLanguage", "");
        errorMessages.put("wrongFormat", "");
        errorMessages.put("error", "");
    }

    @FXML
    public void initialize() {
        xlabel.setText("X: ");
        ylabel.setText("Y: ");
        langConfirmIntro.setText("OK");
        FillOption[] fillOptions = {Low, Medium, High};
        fillChoosing.getItems().addAll(fillOptions);
        Language[] languages = Language.values();
        langChooseIntro.getItems().addAll(languages);
        try {
            Locale enLanguage = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.introLangData", enLanguage);
            updateUI(bundle);

        } catch (MissingResourceException e) {
            System.out.println("no resource");
        }
    }

    public void startSimulation(ActionEvent actionEvent) throws IOException {
        if (xvalue.getText().isEmpty() || yvalue.getText().isEmpty()) {
            showAlert(errorMessages.get("noDim"), true);
            return;
        }
        try {
            Integer.parseInt(xvalue.getText());
            Integer.parseInt(yvalue.getText());
        } catch (NumberFormatException apud) {
            showAlert(errorMessages.get("wrongFormat"), true);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) <= 0 || Integer.parseInt(yvalue.getText()) <= 0) {
            showAlert(errorMessages.get("wrongFormat"), true);
            return;
        }
        if (fillChoosing.getSelectionModel().getSelectedItem() == null) {
            showAlert(errorMessages.get("noBoardFilling"), false);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) > 10 || Integer.parseInt(yvalue.getText()) > 10) {
            showAlert(errorMessages.get("tooHighDim"), true);
            return;
        }
        if (Integer.parseInt(xvalue.getText()) < 2 || Integer.parseInt(yvalue.getText()) < 2) {
            showAlert(errorMessages.get("tooLowDim"), true);
            return;
        }
        boardInfo.setFillPercentage(fillChoosing.getSelectionModel().getSelectedItem().getValue());
        boardInfo.setRow(Integer.parseInt(yvalue.getText()));
        boardInfo.setCol(Integer.parseInt(xvalue.getText()));
        Stage stage = (Stage) startButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent mainRoot = loader.load();
        Scene mainScene = new Scene(mainRoot);

        MainSceneController mainController = loader.getController();
        stage.setScene(mainScene);
        mainController.initializeBoard(boardInfo);
        stage.show();

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

    public void changeLangIntro(ActionEvent actionEvent) {
        if (langChooseIntro.getSelectionModel().getSelectedItem() == null) {
            showAlert(errorMessages.get("noLanguage"), true);
            return;
        }
        switch (langChooseIntro.getSelectionModel().getSelectedItem()) {
            case ENGLISH -> {
                Locale enLang = new Locale.Builder().setLanguage("en").build();
                ResourceBundle bundle = ResourceBundle.getBundle("com.example.introLangData", enLang);
                Locale.setDefault(enLang);
                updateUI(bundle);
                break;
            }
            case POLISH -> {
                Locale plLang = new Locale.Builder().setLanguage("pl").build();
                ResourceBundle bundle = ResourceBundle.getBundle("com.example.introLangData", plLang);
                Locale.setDefault(plLang);
                updateUI(bundle);
                break;
            }
            default -> {
                showAlert(errorMessages.get("noLanguage"), false);
            }
        }
    }

    private void updateUI(ResourceBundle bundle) {

        title.setText(bundle.getString("title"));
        dimChoose.setText(bundle.getString("dimChoose"));
        chooseFillAmountTitle.setText(bundle.getString("chooseFillAmountTitle"));
        xlabel.setText("X:");
        ylabel.setText("Y:");
        langChangeTitleIntro.setText(bundle.getString("langChangeTitleIntro"));
        errorMessages.put("noBoardFilling", bundle.getString("noBoardFilling"));
        errorMessages.put("tooHighDim", bundle.getString("tooHighDim"));
        errorMessages.put("tooLowDim", bundle.getString("tooLowDim"));
        errorMessages.put("noDim", bundle.getString("noDim"));
        errorMessages.put("noLanguage", bundle.getString("noLanguage"));
        errorMessages.put("wrongFormat", bundle.getString("wrongFormat"));
        errorMessages.put("error", bundle.getString("error"));
    }
}
