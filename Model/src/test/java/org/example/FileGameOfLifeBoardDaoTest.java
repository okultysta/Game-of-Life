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


    @Test
    public void FileGameOfLifeBoardDaoReadTest() throws Exception {
        dao.write(board);
        GameOfLifeBoard board2 = dao.read();
        assertEquals(board.getBoard().length, board2.getBoard().length);
        assertEquals(board.getBoard()[0].length, board2.getBoard()[0].length);
        assertEquals(board, board2);
        assertEquals(board.getRow(0), board2.getRow(0));
        assertEquals(board.getColumn(1), board2.getColumn(1));
        assertEquals(board.getColumn(0), board2.getColumn(0));
        assertEquals(board.hashCode(), board2.hashCode());

        //assertThrows(FileNotFoundException.class, () -> new ObjectOutputStream(new FileOutputStream("")));
       // try (FileGameOfLifeBoardDao writer1 = new FileGameOfLifeBoardDao("")) {
        //    assertThrows(RuntimeException.class, writer1::read);
        //}

        //try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename))) {
       //     writer.writeObject(new fakeClass());
      //  }

        try (FileGameOfLifeBoardDao dao = factory.getFileDao(" awtgf87wey 89v wbc8ybey7yn8vn79")) {
            assertThrows(DaoException.class, dao::read);
        }

    }

    @Test
    public void FileGameOfLifeBoardDaoCloseTest() throws Exception {
        FileGameOfLifeBoardDao writer1 = factory.getFileDao(filename);
        assertDoesNotThrow(writer1::close);

    }
}

