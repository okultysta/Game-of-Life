package com.example;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;


public class MainSceneController {
    @FXML
    private Button langConfirm;
    @FXML
    private ChoiceBox<Language> langChooseMain;
    @FXML
    private Text langChooseTitle;
    @FXML
    private Button backToStart;
    @FXML
    private Button doStep;
    @FXML
    private Button saveFile;
    @FXML
    private Button readFile;
    @FXML
    private GridPane mainBoard;
    @FXML
    private Text boardTitle;
    private GameOfLifeBoard gameOfLifeBoard;
    private GameOfLifeBoardDaoFactory factory;
    private TreeMap<String, String> errorMessages;


    public MainSceneController() {
    }


    public void initialize() {
        errorMessages = new TreeMap<>();
        errorMessages.put("error", "");
        errorMessages.put("noLanguage", "");
        errorMessages.put("noFile", "");
        try {
            Locale language = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.langData", language);
            updateUI(bundle);

        } catch (MissingResourceException e) {
            System.out.println("no resource");
        }
        Language[] languages = Language.values();
        langChooseMain.getItems().addAll(languages);
        factory = new GameOfLifeBoardDaoFactory();
    }

    public void initializeBoard(BoardInformation boardInformation) {
        int x = boardInformation.getRow();
        int y = boardInformation.getCol();
        if (mainBoard == null) {
            mainBoard = new GridPane();
        }
        mainBoard.setPrefWidth(25 * x);
        mainBoard.setPrefHeight(25 * y);
        mainBoard.setHgap(0);
        mainBoard.setVgap(0);

        for (int i = 0; i < x; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / x);
            mainBoard.getColumnConstraints().add(column);
        }

        for (int i = 0; i < y; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / y);
            mainBoard.getRowConstraints().add(row);
        }

        mainBoard.getChildren().clear();
        gameOfLifeBoard = new GameOfLifeBoard(x, y, new PlainGameOfLifeSimulator(),
                boardInformation.getFillPercentage());
        //gameOfLifeBoardFactory = new GameOfLifeBoardFactory(gameOfLifeBoard);
        //JavaBeanObjectPropertyBuilder colorPropertyBuilder = JavaBeanObjectPropertyBuilder.create();
        setCellsAndBindings(x, y);
    }

    public void nextStep(ActionEvent actionEvent) {
        gameOfLifeBoard.doSimulationStep();
        updateBoard();
    }

    public void cellClicked(MouseEvent mouseEvent) {
        Rectangle clicked = (Rectangle) mouseEvent.getSource();
        if (clicked.getFill() == Color.GREEN) {
            clicked.setFill(Color.RED);
        } else {
            clicked.setFill(Color.GREEN);
        }
    }


    public void saveToFile(ActionEvent actionEvent) {
        try {
            FileGameOfLifeBoardDao saver = factory.getFileDao("testFile.ser");
            saver.write(gameOfLifeBoard);
            //FileGameOfLifeBoardDao saver2 = factory.getFileDao("OriginalFile.ser");


        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.get("noFile"));
            alert.showAndWait();
        }
    }

    public void readFromFile(ActionEvent actionEvent) {
        try {
            FileGameOfLifeBoardDao saver = factory.getFileDao("testFile.ser");
            gameOfLifeBoard = saver.read();
            setCellsAndBindings(gameOfLifeBoard.getBoard().length, gameOfLifeBoard.getBoard().length);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.get("noFile"));
            alert.showAndWait();
        }
        updateBoard();

    }

    public void confirmLang(ActionEvent actionEvent) {
        Locale selectedLocale;
        if (langChooseMain.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.get("noLanguage"));
            alert.showAndWait();
        }

        switch (langChooseMain.getSelectionModel().getSelectedItem()) {
            case ENGLISH -> {
                selectedLocale = new Locale.Builder().setLanguage("en").build();
            }
            case POLISH -> {
                selectedLocale = new Locale.Builder().setLanguage("pl").build();
            }
            default -> {
                selectedLocale = Locale.getDefault();

            }
        }

        Locale.setDefault(selectedLocale);
        reloadFxml(selectedLocale);
    }


    private void updateUI(ResourceBundle bundle) {
        boardTitle.setText(bundle.getString("boardTitle"));
        langChooseTitle.setText(bundle.getString("langChooseTitle"));
        saveFile.setText(bundle.getString("saveFile"));
        readFile.setText(bundle.getString("readFile"));
        doStep.setText(bundle.getString("doStep"));
        backToStart.setText(bundle.getString("goBackToStart"));
        langConfirm.setText("OK");
        errorMessages.put("error", "");
        errorMessages.put("noLanguage", "");
        errorMessages.put("noFile", "");

    }

    private void updateBoard() {
        int counter = 0;
        for (int i = 0; i < gameOfLifeBoard.getBoard().length; i++) {
            for (int j = 0; j < gameOfLifeBoard.getBoard()[0].length; j++) {
                Rectangle rectangle = (Rectangle) mainBoard.getChildren().get(counter++);
                if (gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                    rectangle.setFill(Color.GREEN);
                } else {
                    rectangle.setFill(Color.RED);
                }
            }
        }
    }

    public void goBackToStart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IntroScene.fxml"));
        Parent mainRoot = loader.load();

        Stage stage = (Stage) backToStart.getScene().getWindow();

        stage.setScene(new Scene(mainRoot));
        stage.show();
    }

    private void reloadFxml(Locale locale) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("introScene.fxml"));
            ResourceBundle bundle = ResourceBundle
                    .getBundle("com.example.langData", locale);
            loader.setResources(bundle);
            Parent root = loader.load();
            Stage stage = (Stage) langChooseMain.getScene().getWindow();
            stage.setScene(new Scene(root));
            updateUI(bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCellsAndBindings(int x, int y) {
        JavaBeanBooleanPropertyBuilder propertyBuilder = JavaBeanBooleanPropertyBuilder.create();
        mainBoard.getChildren().clear();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Rectangle cell = new Rectangle(25, 25);
                cell.setOnMouseClicked(this::cellClicked);
                if (gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                    cell.setFill(Color.GREEN); // alive
                    cell.setStroke(Color.BLACK);
                } else {
                    cell.setFill(Color.RED);
                    cell.setStroke(Color.BLACK);
                }
                mainBoard.add(cell, i, j);
                try {

                    //JavaBeanObjectProperty colorProperty = colorPropertyBuilder.bean(cell).name("fill").build();
                    //SimpleStringProperty colorStringProperty = new SimpleStringProperty(bridge[i][j]);
                    JavaBeanBooleanProperty aliveProperty = propertyBuilder
                            .bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                            .getter("isAlive").setter("setCell").build();
                    //JavaBeanBooleanProperty aliveHelperProperty =
                    // propertyBuilder.bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                    // .getter("isAlive").setter("setCell").build();
                    //StringProperty aliveStringProperty = new SimpleStringProperty();
                    aliveProperty.bind(Bindings.when(cell.fillProperty().isEqualTo(Color.GREEN))
                            .then(true)
                            .otherwise(false));
                    aliveProperty.addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            cell.setFill(Color.GREEN);
                        } else {
                            cell.setFill(Color.RED);
                        }
                    });
                    //Bindings.bindBidirectional(aliveStringProperty, aliveProperty, booleanToStringConverter);
                    //Bindings.bindBidirectional(aliveStringProperty, cell.fillProperty(), paintToStringConverter);
                    //colorStringProperty.bindBidirectional(aliveProperty, new CustomBooleanStringConverter());
                    //colorStringProperty.bindBidirectional(cell.fillProperty(), new CustomStringColorConverter());
                    //BooleanProperty aliveTempProperty = new SimpleBooleanProperty();
                    //ObjectProperty<Paint> fillAdapter = new SimpleObjectProperty<>();

                    //fillAdapter.bindBidirectional(cell.fillProperty());

                    //aliveProperty.bindBidirectional((Property<Boolean>) fillAdapter.isEqualTo(Color.GREEN));

                    aliveProperty.addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            cell.setFill(Color.GREEN);
                        } else {
                            cell.setFill(Color.RED);
                        }
                    });

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}




