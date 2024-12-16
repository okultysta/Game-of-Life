package org.example;

public class BoardPrototype {
    private GameOfLifeBoard board;


    public BoardPrototype(GameOfLifeBoard board) {
        this.board = board.clone();
    }

    public GameOfLifeBoard getInstance() {
        return this.board.clone();
    }
}
