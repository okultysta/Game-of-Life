package org.example;


import java.util.List;

public interface Dao<T> extends AutoCloseable {
    T read() throws DaoException;

    void write(T obj) throws DaoException;

    List<String> getBoardsNames() throws DaoException;

}
