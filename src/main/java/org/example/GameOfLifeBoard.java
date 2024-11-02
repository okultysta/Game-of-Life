package org.example;

import java.util.Random;

public class GameOfLifeBoard {
    //table, which serves as game board
    private GameOfLifeCell[][] board;
    private GameOfLifeSimulator simulator;

    // constructor initializing the board with random boolean values, the board dimensions are given as parameters
    public GameOfLifeBoard(int m, int n, GameOfLifeSimulator simulator) {
        Random r = new Random();
        this.board = new GameOfLifeCell[m][n];
        this.simulator = simulator;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new GameOfLifeCell(r.nextBoolean());
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board[i][j] != board[(k + board.length) % board.length]
                                [(l + board[0].length) % board[0].length]) {
                            board[i][j].addNeighbor(board[(k + board.length) % board.length]
                                    [(l + board[0].length) % board[0].length]);
                        }
                    }
                }
            }
        }
    }

    //method adding the missing cells around the main board, necessary for counting the amount of alive cells

    //method printing the board on screen
    /*
    public void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    */


    //method changing the state of the board, according to rules of the algorithm
    public void doSimulationStep() {
        simulator.doStep(this);
    }

    //getter returning the COPY of the state of the board, not the actual one
    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] copiedBoard = new GameOfLifeCell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copiedBoard[i][j] = board[i][j];
            }
        }
        return copiedBoard;
    }

    public void setCell(int x, int y, boolean value) {
        board[x][y].setCell(value);
    }
}



