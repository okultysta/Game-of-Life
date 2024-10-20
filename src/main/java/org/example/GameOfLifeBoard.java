package org.example;

import java.util.Random;

public class GameOfLifeBoard {
    //table, which serves as game board
    private boolean[][] board;

    // constructor initlializing the board with random boolean values, the board dimensions are given as parameters
    public GameOfLifeBoard(int m, int n) {
        Random r = new Random();
        this.board = new boolean[m + 2][n + 2];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                board[i][j] = r.nextBoolean();
            }
        }
        wrap();
    }

    //method adding the missing cells around the main board, necessary for counting the amount of alive cells
    private void wrap() {
        int m = board.length;
        int n = board[0].length;
        //upper edge
        for (int i = 1; i < n - 1; i++) {
            board[0][i] = board[m - 2][i];
        }
        //lower edge
        for (int i = 1; i < n - 1; i++) {
            board[m - 1][i] = board[1][i];
        }
        //left edge
        for (int i = 1; i < m - 1; i++) {
            board[i][0] = board[i][n - 2];
        }
        //right edge
        for (int i = 1; i < m - 1; i++) {
            board[i][m - 1] = board[i][1];
        }
        //corners
        board[0][0] = board[m - 2][n - 2];
        board[0][n - 1] = board[m - 2][1];
        board[m - 1][0] = board[1][n - 2];
        board[m - 1][n - 1] = board[1][1];
    }

    //method printing the board on screen
    public void print() {
        for (int i = board.length - 2; i >= 1; i--) {
            for (int j = board[0].length - 2; j >= 1; j--) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    //method changing the state of the board, according to rules of the algorithm
    public void doStep() {
        //local variable containing information about number of living cells around every cell
        int alive = 0;
        //declaration of a board in which the future status of the board is stored
        boolean[][] temporary;
        temporary = new boolean[board.length - 2][board[0].length - 2];
        //main part of the method
        //we take into action only the inner part of the table, we skip the outer "frame" of cells
        //starting from second index (1), and ending on index equal to size of the temp. (smaller) table
        for (int i = 1; i <= temporary.length; i++) {
            for (int j = 1; j <= temporary[0].length; j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board[k][l]) {
                            alive++;
                        }
                    }
                }
                //if the cell is alive itself, the number must be secremented
                //if the cell is alive and has 2 or 3 neighbours also alive, then stays alive, otherway it dies
                if (board[i][j]) {
                    switch (--alive) {
                        case (2), (3):
                            temporary [i-1][j-1] = true;
                            break;
                        default:
                            temporary[i - 1][j - 1] = false;
                            break;
                    }
                } else if (alive == 3) {
                    temporary[i - 1][j - 1] = true;
                    //if the cell is dead, only 3 alive neighbours makes her alive
                }
            }
        }
        //transferring the cells from temporary board to the real one
        for (int i = 1; i <= temporary.length; i++) {
            for (int j = 1; j <= temporary[0].length; j++) {
                board[i][j] = temporary[i - 1][j - 1];
            }
        }
        wrap();

    }

    //getter returning the COPY of the state of the board, not the actual one
    public boolean[][] getBoard() {
        wrap();
        boolean[][] copiedBoard = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copiedBoard[i][j] = board[i][j];
            }
        }
        return board;
    }
}



