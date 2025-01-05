package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String filename;

    public FileGameOfLifeBoardDao(String filename) {
        this.filename = filename;
    }

    public GameOfLifeBoard read() throws DaoException {
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameOfLifeBoard) read.readObject();
        } catch (IOException e) {
            throw new DaoException("Error reading from file", e);
        } catch (ClassNotFoundException e) {
            throw new DaoException("Wrong class", e);
        }
    }

    public void write(GameOfLifeBoard board) throws DaoException {
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filename))) {
            write.writeObject(board);
        } catch (IOException e) {
            throw new DaoException("Could not save the board to file " + filename, e);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
