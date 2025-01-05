package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface Dao<T> {
    T read() throws  DaoException;

    void write(T obj) throws DaoException;

}
