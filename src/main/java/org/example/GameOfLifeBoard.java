package org.example;

import java.util.ArrayList;
import java.util.Random;

public class GameOfLifeBoard {
    //table, which serves as game board
    private GameOfLifeCell[][] board;
    private GameOfLifeSimulator simulator;
    private GameOfLifeColumnRow[] columns;
    private GameOfLifeColumnRow[] rows;

    // constructor initializing the board with random boolean values, the board dimensions are given as parameters
    public GameOfLifeBoard(int m, int n, GameOfLifeSimulator simulator) {
        this.columns = new GameOfLifeColumnRow[n];
        this.rows = new GameOfLifeColumnRow[m];
        Random r = new Random();
        this.board = new GameOfLifeCell[m][n];
        this.simulator = simulator;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new GameOfLifeCell(r.nextBoolean());
            }
        }

        for (int i = 0; i < board.length; i++) {
            rows[i] = new GameOfLifeColumnRow();
            for (int j = 0; j < board[0].length; j++) {
                rows[i].addCell(board[i][j]);
            }
        }

        for (int i = 0; i < board[0].length; i++) {
            columns[i] = new GameOfLifeColumnRow();
            for (int j = 0; j < board.length; j++) {
                columns[i].addCell(board[j][i]);
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
                System.out.print(board[i][j].isAlive() + " ");
            }
            System.out.println();
        }
    }*/

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

    public GameOfLifeColumnRow getRow(int x) {
        return rows[x];
    }

    public GameOfLifeColumnRow getColumn(int x) {
        return columns[x];
    }
}



