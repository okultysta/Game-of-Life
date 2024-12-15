package com.example;

import javafx.beans.property.adapter.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.example.*;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

public class MainSceneController {
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


    public MainSceneController() {}


    public void initialize() {
        boardTitle.setText("Game Of Life");
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
        JavaBeanBooleanPropertyBuilder propertyBuilder = JavaBeanBooleanPropertyBuilder.create();
        JavaBeanObjectPropertyBuilder<Color> colorPropertyBuilder = JavaBeanObjectPropertyBuilder.create();
        JavaBeanStringPropertyBuilder colorBuilder = JavaBeanStringPropertyBuilder.create();
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
                    JavaBeanBooleanProperty aliveProperty = propertyBuilder.bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                            .getter("isAlive").setter("setCell").build();
                     JavaBeanObjectProperty colorProperty = colorPropertyBuilder.bean(cell).name("fill").build();
                    String bridge = "";
                    SimpleStringProperty colorStringProperty = new SimpleStringProperty(bridge);

                    colorStringProperty.bindBidirectional(aliveProperty, new CustomBooleanStringConverter());
                    colorStringProperty.bindBidirectional(cell.fillProperty(), new CustomStringColorConverter());
                    /*aliveProperty.addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            cell.setFill(Color.GREEN);
                        } else {
                            cell.setFill(Color.RED);
                        }
                    });
                    */

                }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }





                /* Listener: aktualizacja aliveProperty na podstawie zmiany koloru prostokąta
                int finalI = i;
                int finalJ = j;
                cell.fillProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(Color.GREEN)) {
                        gameOfLifeBoard.getBoard()[finalI][finalJ].setCell(true);
                    } else if (newValue.equals(Color.RED)) {
                        gameOfLifeBoard.getBoard()[finalI][finalJ].setCell(false);
                    }
                });
                *
                 */

            }
        }
    }

    public void nextStep(ActionEvent actionEvent) {
        gameOfLifeBoard.doSimulationStep();
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
                gameOfLifeBoard=saver.read();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("No such file!");
                alert.showAndWait();
            }
            for(int i = 0;i < gameOfLifeBoard.getBoard().length;i++) {
                for(int j = 0;j < gameOfLifeBoard.getBoard()[i].length;j++) {
                    if(gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                        Rectangle temp = (Rectangle)mainBoard.getChildren().get(i+j);
                        temp.setFill(Color.GREEN);
                    }
                    else {
                        Rectangle temp = (Rectangle)mainBoard.getChildren().get(i+j);
                        temp.setFill(Color.RED);
                    }
                }
            }

        }

}

