package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcGameOfLifeBoardDaoTest {
    GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
    GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);


    @Test
    public void JDBCWriteTest() {
        GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);
        JdbcGameOfLifeBoardDao writer =  new JdbcGameOfLifeBoardDao();
        JdbcGameOfLifeBoardDao reader =  new JdbcGameOfLifeBoardDao();
        try {
            writer.write(board);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            GameOfLifeBoard boardRead = writer.read();
            assertEquals(board, boardRead);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
