package org.example;


public class GameOfLifeBoardDaoFactory {

    public GameOfLifeBoardDaoFactory() {
    }

    public FileGameOfLifeBoardDao getFileDao() {
        return new FileGameOfLifeBoardDao();
    }

    public FileGameOfLifeBoardDao getFileDao(String filename) {
        return new FileGameOfLifeBoardDao(filename);
    }

    public JdbcGameOfLifeBoardDao getJdbcDao(String dbname) {
        return new JdbcGameOfLifeBoardDao(dbname);
    }

    public JdbcGameOfLifeBoardDao getJdbcDao() {
        return new JdbcGameOfLifeBoardDao();
    }
}