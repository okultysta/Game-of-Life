package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileGameOfLifeBoardDaoTest {
    private String filename = "src/testFile.ser";
    private GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
    FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(filename);
    private GameOfLifeBoard board = new GameOfLifeBoard(1, 2, simulator, dao);


    @Test
    public void FileGameOfLifeBoardDaoWriteTest() throws IOException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename))) {
            dao.write(board);
        }
            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
                GameOfLifeBoard board2 = (GameOfLifeBoard) reader.readObject();
                assertEquals(board.getBoard().length, board2.getBoard().length);
                assertEquals(board, board2);
                assertEquals(board.hashCode(), board2.hashCode());

                assertEquals(board.getRow(0), board2.getRow(0) );
                assertEquals(board.getColumn(1), board2.getColumn(1));
                assertEquals(board.getColumn(0), board2.getColumn(0) );
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            }
    }


    @Test
    public void FileGameOfLifeBoardDaoReadTest() throws IOException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename))) {
            dao.write(board);
            GameOfLifeBoard board2 = dao.read();
            assertEquals(board.getBoard().length, board2.getBoard().length);
            assertEquals(board.getBoard()[0].length, board2.getBoard()[0].length);
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard()[i].length; j++) {
                    assertEquals(board.getBoard()[i][j], board2.getBoard()[i][j]);
                }
            }
            assertEquals(board.getRow(0), board2.getRow(0));
            assertEquals(board.getColumn(1), board2.getColumn(1));
            assertEquals(board.getColumn(0), board2.getColumn(0) );
            assertEquals(board.hashCode(), board2.hashCode());
        }
        catch(IOException e) {
            throw new IOException(e);
        }


    }
}

