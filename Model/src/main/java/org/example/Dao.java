package org.example;

public interface Dao<T> {
    T read() throws  DaoException;

    void write(T obj) throws DaoException;

}
