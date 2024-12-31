package org.example;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class GameOfLifeBoard implements Serializable, Cloneable {
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
        //this.dao = dao;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new GameOfLifeCell(r.nextBoolean());
            }
        }
        setNeighbours();
        setColumnsAndRows();
    }

    public GameOfLifeBoard(int m, int n, GameOfLifeSimulator simulator, int fillPercentage) {
        board = new GameOfLifeCell[m][n];
        this.columns = Arrays.asList(new GameOfLifeColumnRow[n]);
        this.rows = Arrays.asList(new GameOfLifeColumnRow[m]);
        Random r = new Random();
        this.simulator = simulator;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new GameOfLifeCell(r.nextInt(0, 100) <= fillPercentage);
            }
        }
        setNeighbours();
        setColumnsAndRows();
    }

    public GameOfLifeBoard(GameOfLifeCell[][] board, GameOfLifeSimulator simulator) {
        this.board = new GameOfLifeCell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                this.board[i][j] = new GameOfLifeCell(board[i][j].isAlive());
            }
        }
        this.rows = Arrays.asList(new GameOfLifeColumnRow[board.length]);
        this.columns = Arrays.asList(new GameOfLifeColumnRow[board[0].length]);
        setNeighbours();
        setColumnsAndRows();
        this.simulator = simulator;
    }

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

        return new EqualsBuilder().append(board, that.board).append(columns, that.columns)
                .append(rows, that.rows).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(Arrays.deepHashCode(board))
                .append(columns).append(rows).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", Arrays.deepToString(board))
                .append("columns", columns)
                .append("rows", rows)
                .toString();
    }

    private void setNeighbours() {
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

    private void setColumnsAndRows() {

        for (int i = 0; i < board.length; i++) {
            this.rows.set(i, createRow(i));
        }

        for (int i = 0; i < board[0].length; i++) {
            this.columns.set(i, createColumn(i));
        }

    }


    @Override
    public GameOfLifeBoard clone() {
        try {
            GameOfLifeBoard clone = (GameOfLifeBoard) super.clone();
            clone.board = new GameOfLifeCell[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    clone.board[i][j] = new GameOfLifeCell(board[i][j].isAlive());
                }
            }
            clone.setNeighbours();
            clone.columns = Arrays.asList(new GameOfLifeColumnRow[board[0].length]);
            clone.rows = Arrays.asList(new GameOfLifeColumnRow[board.length]);
            clone.setColumnsAndRows();
            clone.simulator = new PlainGameOfLifeSimulator();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed: " + e.getMessage());
        }
    }

}



