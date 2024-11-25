package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable, Serializable {
    private final String filename;

    public FileGameOfLifeBoardDao(String filename) {
        this.filename = filename;
    }

    public GameOfLifeBoard read() throws RuntimeException {
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameOfLifeBoard) read.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(GameOfLifeBoard board) throws IOException {
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filename))) {
            write.writeObject(board);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
