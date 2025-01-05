package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcGameOfLifeBoardDaoTest {
    GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
    GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);


    @Test
    public void JDBCWriteReadTest() {
        GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);
        JdbcGameOfLifeBoardDao writer =  new JdbcGameOfLifeBoardDao();
        JdbcGameOfLifeBoardDao reader =  new JdbcGameOfLifeBoardDao();
        try {
            writer.write(board);

        }
        catch (DaoException e) {
            e.printStackTrace();
        }
        try {
            GameOfLifeBoard boardRead = writer.read();
            assertEquals(board, boardRead);
            assertNotSame(board, boardRead);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTransactionRollbackOnError() {
        GameOfLifeBoard invalidBoard = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            try (JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao("invalidBoard")) {
                dao.write(invalidBoard);
            }
        });

    }

    @Test
    public void testAutoCloseableResources() {
        assertDoesNotThrow(() -> {
            try (JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao("testBoard")) {
                dao.write(new GameOfLifeBoard(4, 3, gameOfLifeSimulator));
            }
        });
    }


}
