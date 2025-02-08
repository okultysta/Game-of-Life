package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String filename;

    public FileGameOfLifeBoardDao(String filename) {
        this.filename = filename;
    }

    public FileGameOfLifeBoardDao() {
        this.filename = "default.ser";
    }

    public GameOfLifeBoard read() throws DaoException {
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameOfLifeBoard) read.readObject();
        } catch (IOException e) {
            throw new ReadWriteFileException("FileReadError", e);
        } catch (ClassNotFoundException e) {
            throw new ReadWriteFileException("ClassNotFound", e);
        }
    }

    public void write(GameOfLifeBoard board) throws DaoException {
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filename))) {
            write.writeObject(board);
        } catch (IOException e) {
            throw new ReadWriteFileException("FileWriteError", e);
        }
    }

    @Override
    public List<String> getBoardsNames() throws DaoException {
        File directoryPath = new File("./");
        File[] files = directoryPath.listFiles();
        List<String> boardsNames = new ArrayList<>();
        try {
            for (File file : files) {
                if (!file.getName().endsWith(".ser")) {
                    continue;
                }
                boardsNames.add(file.getName());
            }
        } catch (NullPointerException e) {
            throw new DaoException("FileReadException", e);
        }
        return boardsNames;
    }

    @Override
    public void delete(String boardName) throws DaoException {
        File directoryPath = new File("./");
        File[] files = directoryPath.listFiles();
        try {
            for (File file : files) {
                if (file.getName().startsWith(boardName)) {
                    if (!file.delete()) {
                        throw new DaoException("FileReadException", null);
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new DaoException("FileReadException", e);
        }
    }

    @Override
    public void close() {
    }
}
