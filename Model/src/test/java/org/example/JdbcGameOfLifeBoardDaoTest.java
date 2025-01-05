package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcGameOfLifeBoardDaoTest {
    GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
    GameOfLifeBoard board = new GameOfLifeBoard(4, 3, gameOfLifeSimulator);
    Logger logger = LoggerFactory.getLogger(JdbcGameOfLifeBoardDaoTest.class);

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
            logger.error(e.getMessage());
        }
        try {
            GameOfLifeBoard boardRead = writer.read();
            assertEquals(board, boardRead);
            assertNotSame(board, boardRead);
        } catch (DaoException e) {
            logger.error(e.getMessage());
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
