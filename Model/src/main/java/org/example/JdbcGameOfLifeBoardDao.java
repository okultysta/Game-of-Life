package org.example;

import java.io.IOException;
import java.sql.*;

public class JdbcGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private String url = "jdbc:postgresql://localhost:5432/GameOfLife";
    private String boardName;

    public JdbcGameOfLifeBoardDao() {
        this.boardName="default";
    }

    public JdbcGameOfLifeBoardDao(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public GameOfLifeBoard read() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "")) {
            PreparedStatement checkBoardStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM board WHERE name = ?");
            checkBoardStatement.setString(1, boardName);
            ResultSet resultSet = checkBoardStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                throw new IllegalArgumentException("Board with name '" + boardName + "' does not exist.");
            }

            try (PreparedStatement dimensionStatement = connection.prepareStatement(
                    "SELECT MAX(x) AS max_x, MAX(y) AS max_y FROM cell " +
                            "JOIN board ON cell.board_id = board.board_id WHERE board.name = ?")) {
                dimensionStatement.setString(1, boardName);
                ResultSet dimensionResultSet = dimensionStatement.executeQuery();

                int maxX = -1;
                int maxY = -1;

                if (dimensionResultSet.next()) {
                    maxX = dimensionResultSet.getInt("max_x");
                    maxY = dimensionResultSet.getInt("max_y");
                }

                if (maxX == -1 || maxY == -1) {
                    throw new IllegalArgumentException("No cells found for board: " + boardName);
                }

                int width = maxX + 1;
                int height = maxY + 1;
                GameOfLifeCell[][] boardArray = new GameOfLifeCell[height][width];

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        boardArray[i][j] = new GameOfLifeCell(false);
                    }
                }

                try (PreparedStatement cellStatement = connection.prepareStatement(
                        "SELECT * FROM cell JOIN board ON cell.board_id = board.board_id WHERE board.name = ?")) {
                    cellStatement.setString(1, boardName);
                    ResultSet cellResultSet = cellStatement.executeQuery();

                    while (cellResultSet.next()) {
                        boolean state = cellResultSet.getBoolean("state");
                        int x = cellResultSet.getInt("x");
                        int y = cellResultSet.getInt("y");

                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            boardArray[y][x].setCell(state);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SQLException("Error loading data from 'cell' table", e);
                }

                return new GameOfLifeBoard(boardArray, new PlainGameOfLifeSimulator());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error during database query execution", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error or SQL query execution error", e);
        }
    }


    @Override
    public void write(GameOfLifeBoard obj) throws IOException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "");
             Statement statement = connection.createStatement()) {

            // Create tables if they do not exist
            statement.execute("CREATE TABLE IF NOT EXISTS board ("
                    +
                    "board_id SERIAL PRIMARY KEY, "
                    +
                    "name VARCHAR(100) NOT NULL"
                    +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS cell ("
                    +
                    "board_id INTEGER, "
                    +
                    "state BOOLEAN, "
                    +
                    "x INTEGER, "
                    +
                    "y INTEGER, "
                    +
                    "PRIMARY KEY (board_id, x, y), "
                    +
                    "FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE ON UPDATE CASCADE"
                    +
                    ");");

            String deleteCellsSql = "DELETE FROM cell WHERE board_id IN (SELECT board_id FROM board WHERE name = ?)";
            try (PreparedStatement deleteCellsStatement = connection.prepareStatement(deleteCellsSql)) {
                deleteCellsStatement.setString(1, boardName);
                deleteCellsStatement.executeUpdate();
            }

            String deleteBoardSql = "DELETE FROM board WHERE name = ?";
            try (PreparedStatement deleteBoardStatement = connection.prepareStatement(deleteBoardSql)) {
                deleteBoardStatement.setString(1, boardName);
                deleteBoardStatement.executeUpdate();
            }

            String boardInsertSql = "INSERT INTO board (name) VALUES (?)";
            try (PreparedStatement boardStatement = connection.prepareStatement(boardInsertSql,
                    Statement.RETURN_GENERATED_KEYS)) {
                boardStatement.setString(1, boardName);
                boardStatement.executeUpdate();

                ResultSet generatedKeys = boardStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int boardId = generatedKeys.getInt(1);


                    String cellInsertSql = "INSERT INTO cell (board_id, state, x, y) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement cellStatement = connection.prepareStatement(cellInsertSql)) {
                        for (int i = 0; i < obj.getBoard().length; i++) {
                            for (int j = 0; j < obj.getBoard()[i].length; j++) {
                                boolean state = obj.getBoard()[i][j].isAlive();

                                cellStatement.setInt(1, boardId);
                                cellStatement.setBoolean(2, state);
                                cellStatement.setInt(3, j);
                                cellStatement.setInt(4, i);
                                cellStatement.addBatch();
                            }
                        }
                        cellStatement.executeBatch();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("Database error", e);
        }
    }

}
