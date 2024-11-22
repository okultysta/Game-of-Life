package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class GameOfLifeBoardDaoFactoryTest {
    @Test
    public void gameOfLifeBoardDaoFactoryProductionTest() throws Exception {
        GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
        assertInstanceOf(FileGameOfLifeBoardDao.class, (factory.getFileDao("src/testFile.txt")));
    }
}
