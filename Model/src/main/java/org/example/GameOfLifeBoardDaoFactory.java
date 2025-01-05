package org.example;

import java.io.IOException;

public class GameOfLifeBoardDaoFactory {

    public GameOfLifeBoardDaoFactory() {
    }

    public FileGameOfLifeBoardDao getFileDao(String filename)  {
        return new FileGameOfLifeBoardDao(filename);
    }
    public JdbcGameOfLifeBoardDao getJdbcDao(String dbname) {
        return new JdbcGameOfLifeBoardDao(dbname);
    }
}