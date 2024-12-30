package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCGameOfLifeBoardDaoTest {
    GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
    GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);


    @Test
    public void JDBCWriteTest() {
        GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);
        JDBCGameOfLifeBoardDao writer =  new JDBCGameOfLifeBoardDao();
        try {
            writer.write(board);
            GameOfLifeBoard boardRead = writer.read();
            assertEquals(board, boardRead);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
