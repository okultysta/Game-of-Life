package org.example;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    public PlainGameOfLifeSimulator() {
    }

    public void doStep(GameOfLifeBoard board) {
        //local variable containing information about number of living cells around every cell
        int alive;
        //declaration of a board in which the future status of the board is stored
        boolean[][] temporary;
        temporary = new boolean[board.getBoard().length][board.getBoard()[0].length];
        //main part of the method
        //By using rest from division by length of board we calculate how many cells around
        //are alive(true), including these that are on the opposite side of board(wrapping)

        for (int i = 0; i < temporary.length; i++) {
            for (int j = 0; j < temporary[0].length; j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board.getBoard()[(k + board.getBoard().length) % board.getBoard().length]
                                [(l + board.getBoard()[0].length) % board.getBoard()[0].length]) {
                            alive++;
                        }
                    }
                }
                //if the cell is alive itself, the number must be secremented
                //if the cell is alive and has 2 or 3 neighbours also alive, then stays alive, otherway it dies
                if (board.getBoard()[i][j]) {
                    switch (--alive) {
                        case (2), (3):
                            temporary[i][j] = true;
                            break;
                        default:
                            temporary[i][j] = false;
                            break;
                    }
                } else if (alive == 3) {
                    temporary[i][j] = true;
                    //if the cell is dead, only 3 alive neighbours makes her alive
                }
            }
        }
        //transferring the cells from temporary board to the real one
        for (int i = 0; i < temporary.length; i++) {
            for (int j = 0; j < temporary[0].length; j++) {
                board.setCell(i, j, temporary[i][j]);
            }
        }
    }
}
