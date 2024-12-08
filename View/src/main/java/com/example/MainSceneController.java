package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.GameOfLifeBoard;
import org.example.PlainGameOfLifeSimulator;

public class MainSceneController {
    public Button doStep;
    @FXML
    private GridPane mainBoard;
    @FXML
    private Text boardTitle;
    private GameOfLifeBoard gameOfLifeBoard;
    //private GameOfLifeBoard initialBoard;


    public MainSceneController() {

    }

    public void initialize() {
        boardTitle.setText("Game Of Life");

    }

    public void initializeBoard(BoardInformation boardInformation) {
        int x = boardInformation.getRow();
        int y = boardInformation.getCol();
        mainBoard.setPrefWidth(25 * x);
        mainBoard.setPrefHeight(25 * y);
        mainBoard.setHgap(0);
        mainBoard.setVgap(0);


        for (int i = 0; i < x; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / x);  //
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
        //initialBoard = (GameOfLifeBoard) gameOfLifeBoard.clone();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Rectangle cell = new Rectangle(25, 25);
                if (gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                    cell.setFill(Color.LIGHTGRAY); //dead cell
                    cell.setStroke(Color.BLACK);
                } else {
                    cell.setFill(Color.WHITE);
                    cell.setStroke(Color.BLACK);
                }
                mainBoard.add(cell, i, j);
            }
        }
    }


    public void nextSTep(ActionEvent actionEvent) {

        gameOfLifeBoard.doSimulationStep();
        int index = 0;
        for (int i = 0; i < gameOfLifeBoard.getBoard().length; i++) {
            for (int j = 0; j < gameOfLifeBoard.getBoard()[0].length; j++) {
                Rectangle cell = (Rectangle) mainBoard.getChildren().get(index++);
                if (gameOfLifeBoard.getBoard()[i][j].isAlive()) {
                    cell.setFill(Color.LIGHTGRAY); //dead cell
                    cell.setStroke(Color.BLACK);
                } else {
                    cell.setFill(Color.WHITE);
                    cell.setStroke(Color.BLACK);
                }
            }
        }
    }
}