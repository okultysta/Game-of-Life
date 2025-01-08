package org.example;

public class BoardPrototype {
    private GameOfLifeBoard board;


    public BoardPrototype(GameOfLifeBoard board) throws BadCloneClassException {
        this.board = board.clone();
    }

    public GameOfLifeBoard getInstance() {
        try {
            return this.board.clone();
        } catch (BadCloneClassException e) {
            throw new RuntimeException(e);
        }
    }
}
