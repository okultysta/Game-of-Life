package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileGameOfLifeBoardDaoTest {

    @Test
    public void FileGameOfLifeBoardDaoReadWriteTest() throws IOException {
        FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao("src/testFile.txt");
        GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(3,3,simulator);
        dao.write(board);
        GameOfLifeBoard board2 = dao.read();
        assertEquals(board, board2);
    }
}
