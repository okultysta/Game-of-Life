package org.example;


import java.util.ArrayList;

import java.util.Random;



public class GameOfLifeBoard {
    //table, which serves as game board
    private ArrayList<ArrayList<GameOfLifeCell>> board;
    private GameOfLifeSimulator simulator;
    private ArrayList<GameOfLifeColumnRow> columns;
    private ArrayList<GameOfLifeColumnRow> rows;

    // constructor initializing the board with random boolean values, the board dimensions are given as parameters
    public GameOfLifeBoard(int m, int n, GameOfLifeSimulator simulator) {
         GameOfLifeCell[][] tempBoard = new GameOfLifeCell[m][n];
        this.columns = new ArrayList<GameOfLifeColumnRow>();
        this.rows = new ArrayList<GameOfLifeColumnRow>();
        Random r = new Random();
        this.simulator = simulator;
        for (int i = 0; i < m; i++) {
            board.add(new ArrayList<GameOfLifeCell>());
            for (int j = 0; j < n; j++) {
                board.get(i).add(j, new GameOfLifeCell(r.nextBoolean()));
                tempBoard[i][j] = new GameOfLifeCell(r.nextBoolean());
            }
        }

        for (int i = 0; i < board.size(); i++) {
            this.rows.add(createRow(i));
        }

        for (int i = 0; i < board.get(0).size(); i++) {
            this.columns.add(createColumn(i));
        }

        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board.get(i).get(j) != board.get((k + board.size())
                                % board.size()).get((l + board.get(0).size())
                                % board.get(0).size())) {
                            board.get(i).get(j).addNeighbor(board.get((k + board.size())
                                    % board.size()).get((l + board.get(0).size()) % board.get(0).size()));
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

    public GameOfLifeColumnRow createColumn(int index) {
        GameOfLifeColumnRow column = new GameOfLifeColumnRow();
        for (int i = 0; i < board.size(); i++) {
            column.addCell(board.get(i).get(index));
        }
        return column;
    }

    public GameOfLifeColumnRow createRow(int index) {
        GameOfLifeColumnRow row = new GameOfLifeColumnRow();
        for (int i = 0; i < board.get(0).size(); i++) {
            row.addCell(board.get(index).get(i));
        }
        return row;
    }

    //method changing the state of the board, according to rules of the algorithm
    public void doSimulationStep() {
        simulator.doStep(this);
    }

    public ArrayList<ArrayList<GameOfLifeCell>> getBoard() {
        return new ArrayList<ArrayList<GameOfLifeCell>>(board);
    }

    //getter returning the COPY of the state of the board, not the actual one


    public void setCell(int x, int y, boolean value) {
        board.get(x).get(y).setCell(value);
    }

    public GameOfLifeColumnRow getRow(int x) {
        return rows.get(x);
    }

    public GameOfLifeColumnRow getColumn(int x) {
        return columns.get(x);
    }
}



