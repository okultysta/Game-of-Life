package org.example;

import java.io.*;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String filename;

    public FileGameOfLifeBoardDao(String filename) {
        this.filename = filename;
    }

    public GameOfLifeBoard read() throws RuntimeException {
        try (BufferedReader read = new BufferedReader(new FileReader(filename))) {
            String[] dimensions = read.readLine().split(" ");
            int firstDim = Integer.parseInt(dimensions[0]);
            int secondDim = Integer.parseInt(dimensions[1]);
            boolean[][] board = new boolean[firstDim][secondDim];
            for (int i = 0; i < firstDim; i++) {
                for (int j = 0; j < secondDim; j++) {
                    board[i][j] = read.readLine().equals("1");
                }
            }

            GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
            return new GameOfLifeBoard(board, simulator);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file", e);
        }
    }

    public void write(GameOfLifeBoard board) throws IOException {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(filename))) {

            write.write(board.getBoard().length + " " + board.getBoard()[0].length);
            write.newLine();

            for (GameOfLifeCell[] row : board.getBoard()) {
                for (GameOfLifeCell cell : row) {
                    write.write(cell.isAlive() ? "1" : "0");
                    write.newLine();
                }
            }
        }
    }

    @Override
    public void close() throws IOException {

    }
}
