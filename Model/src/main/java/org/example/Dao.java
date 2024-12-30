package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface Dao<T> {
    T read() throws SQLException;

    void write(T obj) throws IOException;

}
