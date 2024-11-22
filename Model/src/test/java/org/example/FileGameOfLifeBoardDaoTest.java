package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileGameOfLifeBoardDaoTest {
    private String filaName = "src/testFile.txt";
    private GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoard board = new GameOfLifeBoard(3,5,simulator);


    @Test
    public void FileGameOfLifeBoardDaoWriteTest() throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(filaName));
        FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(filaName);
        dao.write(board);
        assertEquals(board.getBoard().length, (Integer.parseInt(String.valueOf(read.read()))-'0'));
        read.skip(1);
        assertEquals(board.getBoard()[0].length, (Integer.parseInt(String.valueOf(read.read()))-'0'));
        read.skip(2);

        String lineText = "";

        for(int i = 0; i < board.getBoard().length; i++) {
            for(int j = 0; j < board.getBoard()[i].length; j++) {
                lineText = read.readLine();
            }
            assertEquals(lineText, read.readLine());
        }
    }

    @Test
    public void FileGameOfLifeBoardDaoReadTest() throws IOException {
        FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(filaName);
        GameOfLifeBoard board2 = dao.read();
        assertEquals(board, board2);
    }
}
