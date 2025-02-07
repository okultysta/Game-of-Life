package com.example;



import javafx.application.Platform;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(IntroSceneController.class);


    public IntroSceneController() {
        logger.info("IntroSceneController");
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
            try {
                Locale enLanguage = Locale.getDefault();
                ResourceBundle bundle = ResourceBundle.getBundle("com.example.introLangData", enLanguage);
                updateUI(bundle);

            } catch (MissingResourceException e) {
                throw new InvalidResourcesException("badResources", e);
            }
        } catch (InvalidResourcesException e) {
            showAlert(errorMessages.get(e.getMessage()), false);
            System.exit(1);
        }
    }

    public void startSimulation(ActionEvent actionEvent) {
        if (xvalue.getText().isEmpty() || yvalue.getText().isEmpty()) {
            showAlert(errorMessages.get("noDim"), true);
            return;
        }
        try {
            try {
                Integer.parseInt(xvalue.getText());
                Integer.parseInt(yvalue.getText());
            } catch (NumberFormatException apud) {
                throw new NotANumberException(errorMessages.get("wrongFormat"), apud);
            }
        } catch (NotANumberException e) {
            showAlert(errorMessages.get("wrongFormat"), true);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot);
            MainSceneController mainController = loader.getController();
            stage.setScene(mainScene);
            stage.setTitle(errorMessages.get("windowTitleMain"));
            mainController.initializeBoard(boardInfo);
            stage.show();
        } catch (IOException ioe) {
            throw new InvalidSceneFileException("Invalid scene file!", ioe);
        } catch (InvalidSceneFileException e) {
            showAlert(e.getMessage(), false);
            System.exit(1);
        }

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
        logger.warn(message);
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

        Platform.runLater(() -> {
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setTitle(bundle.getString("windowTitle"));
        });
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
        errorMessages.put("windowTitle", bundle.getString("windowTitle"));
        errorMessages.put("windowTitleMain", bundle.getString("windowTitleMain"));
        errorMessages.put("badResources", bundle.getString("badResources"));
    }
}