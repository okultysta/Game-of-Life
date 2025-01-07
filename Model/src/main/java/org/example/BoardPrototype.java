package org.example;

public class BoardPrototype {
    private GameOfLifeBoard board;


    public BoardPrototype(GameOfLifeBoard board) throws BadCloneClass {
        this.board = board.clone();
    }

    public GameOfLifeBoard getInstance() {
        try {
            return this.board.clone();
        } catch (BadCloneClass e) {
            throw new RuntimeException(e);
        }
    }
}
