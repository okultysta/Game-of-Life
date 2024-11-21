package org.example;

import java.io.IOException;

public class GameOfLifeBoardDaoFactory {

     public GameOfLifeBoardDaoFactory() {}

     public FileGameOfLifeBoardDao getFileDao (String filename) throws IOException {
          return new FileGameOfLifeBoardDao(filename);
     }
}