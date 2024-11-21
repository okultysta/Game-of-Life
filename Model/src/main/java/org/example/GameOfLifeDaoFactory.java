package org.example;

public class GameOfLifeDaoFactory {

     public Dao GameofLifeFactory(String filename) {
         return new FileGameOfLifeBoardDao(filename);
     }
}
