package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileGameOfLifeBoardDaoTest {
    private String filaName = "src/testFile.txt";
    private GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
    private GameOfLifeBoard board = new GameOfLifeBoard(1, 2, simulator);


    @Test
    public void FileGameOfLifeBoardDaoWriteTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filaName))) {
            FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(filaName);
            dao.write(board);
        }
        try (BufferedReader read = new BufferedReader(new FileReader(filaName))) {
            String[] dimensions = read.readLine().split(" ");
            assertEquals(1, Integer.parseInt(dimensions[0]));
            assertEquals(2, Integer.parseInt(dimensions[1]));

            for (GameOfLifeCell[] row : board.getBoard()) {
                for (GameOfLifeCell cell : row) {
                    String line = read.readLine();
                    assertEquals(cell.isAlive() ? "1" : "0", line);
                }
            }
        }
    }


    @Test
    public void FileGameOfLifeBoardDaoReadTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filaName))) {
            FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(filaName);
            dao.write(board);
            GameOfLifeBoard board2 = dao.read();
            assertEquals(board.getBoard().length, board2.getBoard().length);
            assertEquals(board.getBoard()[0].length, board2.getBoard()[0].length);
            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard()[i].length; j++) {
                    assertEquals(board.getBoard()[i][j], board2.getBoard()[i][j]);
                }
            }
        }


    }
}

