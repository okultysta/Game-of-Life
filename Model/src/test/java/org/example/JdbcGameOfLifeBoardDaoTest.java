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
        try (JdbcGameOfLifeBoardDao writer =  new JdbcGameOfLifeBoardDao();
        JdbcGameOfLifeBoardDao reader =  new JdbcGameOfLifeBoardDao()) {
            writer.write(board);
            GameOfLifeBoard boardRead = writer.read();
            assertEquals(board, boardRead);
            assertNotSame(board, boardRead);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
    }
    @Test
    public void closeTest() throws Exception {
        JdbcGameOfLifeBoardDao writer =  new JdbcGameOfLifeBoardDao();
        writer.close();
        assertTrue(writer.isClosed());
    }

    @Test
    public void testTransactionRollbackOnError() {
        GameOfLifeBoard invalidBoard = null;
        assertThrows(NullPointerException.class, () -> {
            try (JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao("invalidBoard")) {
                dao.write(invalidBoard);
            }
        });

    }

    @Test
    public void testAutoCloseableResources() {
        JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao("testBoard");
        assertDoesNotThrow(() -> {
            try (JdbcGameOfLifeBoardDao resource = dao) {
                resource.write(new GameOfLifeBoard(4, 3, gameOfLifeSimulator));
            }
        });
        assertTrue(dao.isClosed());
    }

    @Test
    public void testResourcesClosedOnException() {
        JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao("testBoard");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            try (JdbcGameOfLifeBoardDao resource = dao) {
                throw new RuntimeException("wyjątek");
            }
        });

        assertEquals("wyjątek", exception.getMessage());
        assertTrue(dao.isClosed());
    }

    @Test
    public void testGetBoardNames() throws DaoException {
        try (JdbcGameOfLifeBoardDao reader =  new JdbcGameOfLifeBoardDao()) {
            assertDoesNotThrow(() -> {
                reader.getBoardsNames();
            });
        }
    }


}
