package org.example;

import java.util.Random;

public class GameOfLifeBoard {
    private boolean[][] board;

    public GameOfLifeBoard(int m, int n) {
        Random r = new Random();
        this.board = new boolean[m+2][n+2];
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                board[i][j] = r.nextBoolean();
            }
        }
        wrap();
    }

        private void wrap(){
        int m = board.length;
        int n = board[0].length;
       for(int i=1; i<n-1; i++){
           board[0][i] = board[m-2][i];
            }

       for(int i = 1; i<n-1; i++){
                board[m-1][i] = board[1][i];
            }

       for(int i=1; i<m-1; i++){
                board[i][0] = board[i][n-2];
            }

       for(int i = 1; i<m-1; i++){
                board[i][m-1] = board[i][1];
            }

       board[0][0]=board[m-2][n-2];
       board[0][n-1] = board[m-2][1];
       board[m-1][0]=board[1][n-2];
       board[m-1][n-1]=board[1][1];
        }

        public void print() {
            for(int i=board.length-2;i>=1;i--){
                for(int j=board[0].length-2;j>=1;j--) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }
        public void doStep(){
        int alive=0;
        boolean[][] temporary;
        temporary = new boolean[board.length-2][board[0].length-2];
        for(int i=1;i<= temporary.length;i++){
            for(int j=1;j<=temporary[0].length;j++){
                alive=0;
                for(int k=i-1;k<=i+1;k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (board[k][l]) {
                            alive++;
                        }
                    }
                }
                    if(board[i][j])
                        alive--;

                if(board[i][j])
                    switch(alive){
                        case(2):
                        case(3):
                            break;
                        default:
                            temporary[i-1][j-1]=false;
                    }
                else
                    if(alive==3)
                        temporary[i-1][j-1]=true;  //tutaj rzuca wyjatek out of bound
                }
            }
        for(int i=1; i<= temporary.length; i++){
            for(int j=1; j<=temporary[0].length; j++){
                board[i][j]=temporary[i-1][j-1];
            }
        }
            wrap();

        }

    public boolean[][] getBoard() {
        wrap();
        boolean[][] copiedBoard = new boolean[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                copiedBoard[i][j]=board[i][j];
            }
        }
        return board;
    }
}



