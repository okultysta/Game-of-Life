package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GameOfLifeBoard {
    //table, which serves as game board
    private GameOfLifeCell[][] board;
    private GameOfLifeSimulator simulator;
    private List<GameOfLifeColumnRow> columns;
    private List<GameOfLifeColumnRow> rows;

    // constructor initializing the board with random boolean values, the board dimensions are given as parameters
    public GameOfLifeBoard(int m, int n, GameOfLifeSimulator simulator) {
        board = new GameOfLifeCell[m][n];
        this.columns = Arrays.asList(new GameOfLifeColumnRow[n]);
        this.rows = Arrays.asList(new GameOfLifeColumnRow[m]);
        Random r = new Random();
        this.simulator = simulator;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new GameOfLifeCell(r.nextBoolean());
            }
        }

        for (int i = 0; i < board.length; i++) {
            this.rows.set(i, createRow(i));
        }

        for (int i = 0; i < board[0].length; i++) {
            this.columns.set(i, createColumn(i));
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board[i][j] != board[(k + board.length)
                                % board.length][(l + board[0].length)
                                % board[0].length]) {
                            board[i][j].addNeighbor(board[(k + board.length)
                                    % board.length][(l + board[0].length) % board[0].length]);
                        }
                    }
                }
            }
        }
    }


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

    public GameOfLifeColumnRow createColumn(int index) {
        GameOfLifeColumnRow column = new GameOfLifeColumnRow();
        for (int i = 0; i < board.length; i++) {
            column.addCell(board[i][index]);
        }
        return column;
    }

    public GameOfLifeColumnRow createRow(int index) {
        GameOfLifeColumnRow row = new GameOfLifeColumnRow();
        for (int i = 0; i < board[0].length; i++) {
            row.addCell(board[index][i]);
        }
        return row;
    }

    //method changing the state of the board, according to rules of the algorithm
    public void doSimulationStep() {
        simulator.doStep(this);
    }

    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] copy = new GameOfLifeCell[board.length][board[0].length];
        System.arraycopy(board, 0, copy, 0, board.length);
        return copy;
    }

    //getter returning the COPY of the state of the board, not the actual one


    public void setCell(int x, int y, boolean value) {
        board[x][y].setCell(value);
    }

    public GameOfLifeColumnRow getRow(int x) {
        return rows.get(x);
    }

    public GameOfLifeColumnRow getColumn(int x) {
        return columns.get(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameOfLifeBoard that)) {
            return false;
        }
        return Objects.deepEquals(board, that.board) && Objects.equals(simulator, that.simulator)
                && Objects.equals(columns, that.columns) && Objects.equals(rows, that.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), simulator, columns, rows);
    }

    @Override
    public String toString() {
        return "GameOfLifeBoard{"
                + "board=" + Arrays.toString(board)
                + ", simulator=" + simulator
                + ", columns=" + columns
                + ", rows=" + rows + '}';
    }
}



