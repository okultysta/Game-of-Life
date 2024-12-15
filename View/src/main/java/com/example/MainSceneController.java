package com.example;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.property.adapter.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.example.*;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.awt.Color.GREEN;
import static java.awt.Color.RED;


public class MainSceneController {
    @FXML
    private Button langConfirm;
    @FXML
    private ChoiceBox<Language> langChooseMain;
    @FXML
    private Text langChooseTitle;
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
    private GameOfLifeBoardFactory gameOfLifeBoardFactory;


    public MainSceneController() {}


    public void initialize() {
        try {
            //Locale enLanguage = new Locale("en");
           // ResourceBundle bundle = ResourceBundle.getBundle("i18n.introLangData", enLanguage);
            //updateUI(bundle);

        }
        catch (MissingResourceException e) {
            System.out.println("dupa ni ma zasobu");
        }
        Language[] languages = Language.values();
        langChooseMain.getItems().addAll(languages);
        factory = new GameOfLifeBoardDaoFactory();
    }

    public void initializeBoard(BoardInformation boardInformation) {
        int x = boardInformation.getRow();
        int y = boardInformation.getCol();
        if(mainBoard == null) {
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
        gameOfLifeBoardFactory = new GameOfLifeBoardFactory(gameOfLifeBoard);
        JavaBeanBooleanPropertyBuilder propertyBuilder = JavaBeanBooleanPropertyBuilder.create();
        JavaBeanObjectPropertyBuilder colorPropertyBuilder = JavaBeanObjectPropertyBuilder.create();

        String[][] bridge = new String[x][y];
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
                    JavaBeanBooleanProperty aliveProperty = propertyBuilder.bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                            .getter("isAlive").setter("setCell").build();
                    //JavaBeanBooleanProperty aliveHelperProperty = propertyBuilder.bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                           // .getter("isAlive").setter("setCell").build();
                    StringProperty aliveStringProperty = new SimpleStringProperty();
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
                    /*
                    aliveProperty.addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            cell.setFill(Color.GREEN);
                        } else {
                            cell.setFill(Color.RED);
                        }
                    });



                    int finalI = i;
                    int finalJ = j;
                    cell.fillProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue.equals(Color.GREEN)) {
                            gameOfLifeBoard.getBoard()[finalI][finalJ].setCell(true);
                        } else if (newValue.equals(Color.RED)) {
                            gameOfLifeBoard.getBoard()[finalI][finalJ].setCell(false);
                        }
                    });

                    */


                }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }







            }
        }
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

    public GameOfLifeBoard getGameOfLifeBoard() {
        return gameOfLifeBoard;
    }



    public GridPane getMainBoard() {
        return mainBoard;
    }

    public void saveToFile(ActionEvent actionEvent) {
        try {
            FileGameOfLifeBoardDao saver = factory.getFileDao("testFile.ser");
            saver.write(gameOfLifeBoard);
            FileGameOfLifeBoardDao saver2 = factory.getFileDao("OriginalFile.ser");
            saver2.write(gameOfLifeBoardFactory.createInstance());

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("No such file!");
            alert.showAndWait();
        }
    }

        public void readFromFile(ActionEvent actionEvent) {
            try {
                FileGameOfLifeBoardDao saver = factory.getFileDao("testFile.ser");
                gameOfLifeBoard = saver.read();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("No such file!");
                alert.showAndWait();
            }
            updateBoard();

        }

    public void confirmLang(ActionEvent actionEvent) {
        switch (langChooseMain.getSelectionModel().getSelectedItem()) {
            case ENGLISH -> {
                Locale enLang = new Locale("en");
                ResourceBundle bundle = ResourceBundle.getBundle("i18n.introLangData", enLang);
                Locale.setDefault(enLang);
                updateUI(bundle);
                break;
            }
            case POLISH -> {
                Locale plLang = new Locale("pl");
                ResourceBundle bundle = ResourceBundle.getBundle("i18n.introLangData", plLang);
                Locale.setDefault(plLang);
                updateUI(bundle);
                break;
            }

        }
    }


    private void updateUI (ResourceBundle bundle) {
            boardTitle.setText(bundle.getString("boardTitle"));
            langChooseTitle.setText(bundle.getString("langChooseTitle"));
            saveFile.setText(bundle.getString("saveFile"));
            readFile.setText(bundle.getString("readFile"));
            doStep.setText(bundle.getString("doStep"));
        }

        private void updateBoard() {
        int counter =0;
        for(int i=0;i<gameOfLifeBoard.getBoard().length;i++) {
            for(int j=0;j<gameOfLifeBoard.getBoard()[0].length;j++) {
                Rectangle rectangle = (Rectangle)mainBoard.getChildren().get(counter++);
                if(gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                    rectangle.setFill(Color.GREEN);
                }
                else {
                    rectangle.setFill(Color.RED);
                }
            }
        }
    }
}




