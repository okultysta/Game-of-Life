package com.example;


import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;
import org.example.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;


public class MainSceneController {
    @FXML
    private Button readJdbc;
    @FXML
    private Button writeDB;
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
    private static final Logger logger = LoggerFactory.getLogger(MainSceneController.class);

    public MainSceneController() {
    }


    public void initialize() {
        errorMessages = new TreeMap<>();
        errorMessages.put("error", "");
        errorMessages.put("noLanguage", "");
        errorMessages.put("noFile", "");
        errorMessages.put("noDatabase", "");
        try {
            Locale language = Locale.getDefault();
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.langData", language);
            updateUI(bundle);

        } catch (MissingResourceException e) {
            logger.error("no resource!");
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
        String fileNameCurr = "";
        try {
            fileNameCurr = getBoardName();
            if (fileNameCurr.isEmpty()) {
                throw new IllegalArgumentException(errorMessages.get("noBoardName"));
            }
        } catch (ObjectNotFoundException e) {
            showError(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }
        try {
            Dao<GameOfLifeBoard> saver = factory.getFileDao(fileNameCurr);
            saver.write(gameOfLifeBoard);

        } catch (DaoException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void readFromFile(ActionEvent actionEvent) {
        String fileNameCurr = "";
        try (Dao<GameOfLifeBoard> dao = factory.getFileDao()) {
            ChoiceDialog<String> chooseBoard = new ChoiceDialog<>("", dao.getBoardsNames());
            chooseBoard.setTitle(errorMessages.get("chooseBoard"));
            chooseBoard.setHeaderText(errorMessages.get("chooseBoard") + "!");
            chooseBoard.setContentText(errorMessages.get("chooseBoard") + ":");
            chooseBoard.showAndWait();
            fileNameCurr = chooseBoard.getSelectedItem();

            if (fileNameCurr.isEmpty()) {
                throw new FileException(errorMessages.get("FileReadError"), null);
            }
            try (Dao<GameOfLifeBoard> selectedDao = factory.getFileDao(fileNameCurr)) {
                gameOfLifeBoard = selectedDao.read();
                setCellsAndBindings(gameOfLifeBoard.getBoard().length, gameOfLifeBoard.getBoard()[0].length);
                updateBoard();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(errorMessages.get("error"));
                alert.setHeaderText(null);
                alert.setContentText(errorMessages.get("FileReadError"));
                alert.showAndWait();
                logger.error("Error reading board: {}", errorMessages.get("FileReadError"));
            }
        } catch (FileException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            logger.error("Error reading board: {}", e.getMessage());
        } catch (DaoException e) {
            showError(errorMessages.get(e.getMessage()));
            logger.error("DAO exception: {}", e.getMessage());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            logger.error("Error: {}", e.getMessage());
        }
        try (Dao<GameOfLifeBoard> saver = factory.getFileDao(fileNameCurr)) {
            gameOfLifeBoard = saver.read();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //setCellsAndBindings(gameOfLifeBoard.getBoard().length, gameOfLifeBoard.getBoard().length);
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
        errorMessages.clear();
        boardTitle.setText(bundle.getString("boardTitle"));
        langChooseTitle.setText(bundle.getString("langChooseTitle"));
        saveFile.setText(bundle.getString("saveFile"));
        readFile.setText(bundle.getString("readFile"));
        doStep.setText(bundle.getString("doStep"));
        backToStart.setText(bundle.getString("goBackToStart"));
        langConfirm.setText("OK");
        errorMessages.put("error", bundle.getString("error"));
        errorMessages.put("noLanguage", bundle.getString("noLanguage"));
        errorMessages.put("noFile", bundle.getString("noFile"));
        writeDB.setText(bundle.getString("writeDB"));
        readJdbc.setText(bundle.getString("readJDBC"));
        errorMessages.put("noDB", bundle.getString("noDB"));
        errorMessages.put("enterTableName", bundle.getString("enterTableName"));
        errorMessages.put("wrongScene", bundle.getString("wrongScene"));
        errorMessages.put("FileReadError", bundle.getString("FileReadError"));
        errorMessages.put("DbConnectError", bundle.getString("DbConnectError"));
        errorMessages.put("BadStatement", bundle.getString("BadStatement"));
        errorMessages.put("FileWriteError", bundle.getString("FileWriteError"));
        errorMessages.put("BoardsNamesError", bundle.getString("BoardsNamesError"));
        errorMessages.put("BadInsert", bundle.getString("BadInsert"));
        errorMessages.put("BadBoardDims", bundle.getString("BadBoardDims"));
        errorMessages.put("TransactionFailed", bundle.getString("TransactionFailed"));
        errorMessages.put("enterDbName", bundle.getString("enterDbName"));
        errorMessages.put("typeDBName", bundle.getString("typeDBName"));
        errorMessages.put("dbName", bundle.getString("dbName"));
        errorMessages.put("chooseBoard", bundle.getString("chooseBoard"));
        errorMessages.put("boardSaved", bundle.getString("boardSaved"));
        errorMessages.put("wrongInit", bundle.getString("wrongInit"));
        errorMessages.put("badBoardsNames", bundle.getString("badBoardsNames"));
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
            throw new InvalidSceneFileException(errorMessages.get("wrongScene"), e);
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


                    JavaBeanBooleanProperty aliveProperty = propertyBuilder
                            .bean(gameOfLifeBoard.getBoard()[i][j]).name("alive")
                            .getter("isAlive").setter("setCell").build();
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
                    aliveProperty.addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            cell.setFill(Color.GREEN);
                        } else {
                            cell.setFill(Color.RED);
                        }
                    });

                } catch (NoSuchMethodException e) {
                    try {
                        throw new PropertyBuildException(errorMessages.get("wrongInit"), e);
                    } catch (PropertyBuildException ex) {
                        showError(ex.getMessage());
                    }
                }


            }
        }
    }


    public void readJdbc(ActionEvent actionEvent) {
        String dbNameCurr = "";
        try (JdbcGameOfLifeBoardDao dao = factory.getJdbcDao()) {
            ChoiceDialog<String> chooseBoard = new ChoiceDialog<>("", dao.getBoardsNames());
            chooseBoard.setTitle(errorMessages.get("chooseBoard"));
            chooseBoard.setHeaderText(errorMessages.get("chooseBoard") + "!");
            chooseBoard.setContentText(errorMessages.get("chooseBoard") + ":");
            chooseBoard.showAndWait();
            dbNameCurr = chooseBoard.getSelectedItem();

            if (dbNameCurr.isEmpty()) {
                throw new DbReadWriteException(errorMessages.get("noDB"), null);
            }

            try (JdbcGameOfLifeBoardDao selectedDao = factory.getJdbcDao(dbNameCurr)) {
                gameOfLifeBoard = selectedDao.read();
                setCellsAndBindings(gameOfLifeBoard.getBoard().length, gameOfLifeBoard.getBoard()[0].length);
                updateBoard();
            }
        } catch (DbReadWriteException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorMessages.get("error"));
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            logger.error("Error reading board: {}", e.getMessage());
        } catch (DaoException e) {
            showError(errorMessages.get(e.getMessage()));
            logger.error("DAO exception: {}", e.getMessage());
        }
    }

    private String getBoardName() throws ObjectNotFoundException {
        TextInputDialog dialog = new TextInputDialog("default");
        dialog.setTitle(errorMessages.get("enterDbName"));
        dialog.setHeaderText(errorMessages.get("typeDBName"));
        dialog.setContentText(errorMessages.get("dbName"));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isBlank()) {
            return result.get();
        } else {
            throw new ObjectNotFoundException(errorMessages.get("enterTableName"), null);
        }
    }


    public void writeDB(ActionEvent actionEvent) {
        String dbNameCurr = "";
        try {
            dbNameCurr = getBoardName();
            if (dbNameCurr.isEmpty()) {
                throw new IllegalArgumentException(errorMessages.get("noBoardName"));
            }
        } catch (ObjectNotFoundException e) {
            showError(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }

        try (Dao<GameOfLifeBoard> dao = factory.getJdbcDao(dbNameCurr)) {
            dao.write(gameOfLifeBoard);
            logger.info("{} {}", errorMessages.get("boardSaved"), dbNameCurr);
        } catch (DaoException e) {
            showError(errorMessages.get(e.getMessage()));
            logger.error(e.getMessage());
        } catch (Exception e) {
            showError(errorMessages.get("badBoardsNames"));
            logger.error(errorMessages.get("badBoardsNames"));

        }
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorMessages.get("error"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}