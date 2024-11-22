package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Dao<T> {
    T read() throws FileNotFoundException;

    void write(T obj) throws IOException;

}
