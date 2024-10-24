package org.example;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    public void doStep(GameOfLifeBoard board){
        boolean [][]  current= new boolean[board.getBoard().length+2][board.getBoard().length+2];
        for(int i=0;i<board.getBoard().length;i++){
            for(int j=0;j<board.getBoard()[0].length;j++){
                current[i+1][j+1]=board.getBoard()[i][j];
            }
        }
        //local variable containing information about number of living cells around every cell
        int alive = 0;
        //declaration of a board in which the future status of the board is stored
        boolean[][] temporary;
        temporary = new boolean[current.length - 2][current[0].length - 2];
        //main part of the method
        //we take into action only the inner part of the table, we skip the outer "frame" of cells
        //starting from second index (1), and ending on index equal to size of the temp. (smaller) table
        wrap(current);

        for (int i = 1; i <= temporary.length; i++) {
            for (int j = 1; j <= temporary[0].length; j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (current[k][l]) {
                            alive++;
                        }
                    }
                }
                //if the cell is alive itself, the number must be secremented
                //if the cell is alive and has 2 or 3 neighbours also alive, then stays alive, otherway it dies
                if (current[i][j]) {
                    switch (--alive) {
                        case (2), (3):
                            temporary[i - 1][j - 1] = true;
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
        for (int i = 0; i < temporary.length; i++) {
            for (int j = 0; j < temporary[0].length; j++) {
                board.setCell(i, j, temporary[i][j]);
            }
        }


    }

    private void wrap(boolean[][] board) {
        boolean [][] temp = new boolean[board.length+2][board[0].length+2];
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


}
