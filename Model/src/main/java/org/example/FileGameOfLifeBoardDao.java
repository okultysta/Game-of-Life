package org.example;

import java.io.*;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private FileReader reader;
    private FileWriter writer;

    public FileGameOfLifeBoardDao(String filename) throws IOException {
        this.reader = new FileReader(filename);
        this.writer = new FileWriter(filename);
    }

    public GameOfLifeBoard read() throws RuntimeException {

        try (BufferedReader read = new BufferedReader(reader)) {
            int firstDim = (Integer.parseInt(String.valueOf(read.read()))-'0');
            read.skip(1);
            int secondDim = (Integer.parseInt(String.valueOf(read.read()))-'0');
            boolean[][] board = new boolean[firstDim][secondDim];
            for (int i = 0; i < firstDim; i++) {
                for (int j = 0; j < secondDim; j++) {
                    board[i][j] = read.readLine().charAt(0) == '1';
                }
            }
            GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
            return new GameOfLifeBoard(board, simulator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void write(GameOfLifeBoard board) throws IOException {
        try (BufferedWriter write = new BufferedWriter(writer)) {
            write.write(board.getBoard().length + " " + board.getBoard()[0].length);
            write.newLine();

            for (int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard()[i].length; j++) {
                    write.write(board.getBoard()[i][j].isAlive() ? "1" : "0");
                }
                write.newLine();
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (reader != null && writer != null) {
            reader.close();
            writer.close();
        }
    }
}







