package com.example;

import org.example.GameOfLifeBoard;

public class GameOfLifeBoardFactory {
    private final GameOfLifeBoard board;
    public GameOfLifeBoardFactory(GameOfLifeBoard board) {
        this.board = board;
    }
    public GameOfLifeBoard createInstance() {
        return board.clone();
    }
}
