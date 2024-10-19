package org.example;

import java.util.Random;

public class GameOfLifeBoard {
    private boolean[][] board;

    public GameOfLifeBoard(int m, int n) {
        Random r = new Random();
        this.board = new boolean[m+2][n+2];
        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
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
            }
        }

        public void doStep(){
        int alive=0;
        for(int i=board.length-1;i>=0;i--){
            for(int j=board[0].length-1;j>=0;j--){
                alive=0;
                for(int k=i-1;k<=i+1;k++){
                    for(int l=j-1;l<=j+1;l++){
                        if(board[k][l]){
                            alive++;
                        }
                    }
                    if(board[i][j])
                        alive--;
                }
                if(board[i][j])
                    switch(alive){
                        case(2):
                        case(3):
                            break;
                        default:
                            board[i][j]=false;
                    }
                else
                    if(alive==3)
                        board[i][j]=true;
                }
            }
        }

    }

