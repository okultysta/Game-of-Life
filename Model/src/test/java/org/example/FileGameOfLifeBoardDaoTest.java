package org.example;


import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileGameOfLifeBoardDaoTest {
    private String filename = "src/testFile.ser";
    private GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
    private FileGameOfLifeBoardDao dao = factory.getFileDao(filename);
    private GameOfLifeBoard board = new GameOfLifeBoard(1, 2, simulator);

    public FileGameOfLifeBoardDaoTest() throws IOException {
    }

    private static class fakeClass implements Serializable {
        int data = 10;
    }

    @Test
    public void FileGameOfLifeBoardDaoWriteTest()  {
        try {
            dao.write(board);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        GameOfLifeBoard board2 = null;
        try {
            board2 = (GameOfLifeBoard) dao.read();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        assertEquals(board.getBoard().length, board2.getBoard().length);
        assertEquals(board, board2);
        assertEquals(board.hashCode(), board2.hashCode());
        assertEquals(board.getRow(0), board2.getRow(0));
        assertEquals(board.getColumn(1), board2.getColumn(1));
        assertEquals(board.getColumn(0), board2.getColumn(0));

    }

    /*
    @Test
    public void FileGameOfLifeBoardDaoReadTest() throws Exception {
        Dao<GameOfLifeBoard> dao2 = factory.getFileDao("tralala.ser");
        GameOfLifeBoard board2= new GameOfLifeBoard(5, 2, simulator);
        dao2.write(board2);
        GameOfLifeBoard boardRead = dao.read();
        assertEquals(board2.getBoard().length, boardRead.getBoard().length);
        assertEquals(board2.getBoard()[0].length, boardRead.getBoard()[0].length);
        assertEquals(board2, boardRead);
        assertEquals(board2.getRow(0), boardRead.getRow(0));
        assertEquals(board2.getColumn(1), boardRead.getColumn(1));
        assertEquals(board2.getColumn(0), boardRead.getColumn(0));
        assertEquals(board2.hashCode(), boardRead.hashCode());

        try (FileGameOfLifeBoardDao dao = factory.getFileDao(" awtgf87wey 89v wbc8ybey7yn8vn79")) {
            assertThrows(DaoException.class, dao::read);
        }

    }

     */

    @Test
    public void FileGameOfLifeBoardDaoCloseTest() throws Exception {
        FileGameOfLifeBoardDao writer1 = factory.getFileDao(filename);
        assertDoesNotThrow(writer1::close);

    }
}

