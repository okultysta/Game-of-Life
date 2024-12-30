package org.example;


import java.io.IOException;
import java.sql.*;


public class JDBCGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private String url = "jdbc:postgresql://localhost:5432/GameOfLife";
    private String boardName="dupa";

    @Override
    public GameOfLifeBoard read() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "");
             Statement statement = connection.createStatement()) {

            String dimensionQuery = "SELECT MAX(x) AS max_x, MAX(y) AS max_y FROM cell "
                    + "JOIN board ON cell.board_id = board.board_id WHERE board.name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(dimensionQuery)) {
                preparedStatement.setString(1, boardName);

                ResultSet resultSet = preparedStatement.executeQuery();

                int maxX = 0;
                int maxY = 0;

                if (resultSet.next()) {
                    maxX = resultSet.getInt("max_x");
                    maxY = resultSet.getInt("max_y");
                }

                // Inicjalizacja planszy jako 2D tablica GameOfLifeCell
                int width = maxX + 1;
                int height = maxY + 1;

                GameOfLifeCell[][] boardArray = new GameOfLifeCell[height][width];

                // Wypełnianie planszy pustymi komórkami
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        boardArray[i][j] = new GameOfLifeCell(false); // Inicjalizacja każdej komórki jako martwej
                    }
                }

                // Zapytanie do pobrania komórek
                String cellQuery = "SELECT * FROM cell "
                        + "JOIN board ON cell.board_id = board.board_id WHERE board.name = ?";

                try (PreparedStatement cellStatement = connection.prepareStatement(cellQuery)) {
                    cellStatement.setString(1, boardName);

                    ResultSet cellResultSet = cellStatement.executeQuery();

                    // Wypełnianie planszy wartościami z bazy danych
                    while (cellResultSet.next()) {
                        boolean state = cellResultSet.getBoolean("state");
                        int x = cellResultSet.getInt("x");
                        int y = cellResultSet.getInt("y");

                        // Sprawdzanie poprawności współrzędnych przed przypisaniem do tablicy
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            boardArray[y][x].setCell(state); // Przypisanie stanu komórki
                        } else {
                            System.out.println("Błąd: Współrzędne (" + x + ", " + y + ") są poza zakresem planszy.");
                        }
                    }

                    return new GameOfLifeBoard(boardArray, new PlainGameOfLifeSimulator());
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Błąd podczas wczytywania danych z tabeli cell", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Błąd połączenia z bazą danych lub wykonania zapytań SQL", e);
        }
    }



    @Override
    public void write(GameOfLifeBoard obj) throws IOException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "");
             Statement statement = connection.createStatement()) {

            // Tworzenie tabel, jeśli nie istnieją
            statement.execute("CREATE TABLE IF NOT EXISTS board ("
                    + "    board_id SERIAL PRIMARY KEY, "
                    + "    name VARCHAR(100) NOT NULL"
                    + ");");

            statement.execute("CREATE TABLE IF NOT EXISTS cell ("
                    + "    board_id INTEGER, "
                    + "    state BOOLEAN, "
                    + "    x INTEGER, "
                    + "    y INTEGER, "
                    + "    PRIMARY KEY (board_id, x, y), "
                    + "    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE ON UPDATE CASCADE"
                    + ");");

            // Wstawienie planszy do tabeli board
            String boardInsertSQL = "INSERT INTO board (name) VALUES (?)";
            try (PreparedStatement boardStatement = connection.prepareStatement(boardInsertSQL, Statement.RETURN_GENERATED_KEYS)) {
                boardStatement.setString(1, boardName); // Ustaw nazwę planszy
                boardStatement.executeUpdate();

                // Pobranie wygenerowanego board_id
                ResultSet generatedKeys = boardStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int boardId = generatedKeys.getInt(1);

                    // Wstawienie komórek do tabeli cell
                    String cellInsertSQL = "INSERT INTO cell (board_id, state, x, y) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement cellStatement = connection.prepareStatement(cellInsertSQL)) {
                        for (int i = 0; i < obj.getBoard().length; i++) {
                            for (int j = 0; j < obj.getBoard()[i].length; j++) {
                                boolean state = obj.getBoard()[i][j].isAlive();

                                cellStatement.setInt(1, boardId);
                                cellStatement.setBoolean(2, state);
                                cellStatement.setInt(3, j); // Kolumna jako x
                                cellStatement.setInt(4, i); // Wiersz jako y
                                cellStatement.addBatch();
                            }
                        }
                        cellStatement.executeBatch();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException("DB error", e);
        }
    }


}
