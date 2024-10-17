package org.example;

import java.util.Random;

public class GameOfLifeBoard {
    private boolean[][] board;

    public GameOfLifeBoard(int m, int n) {
        Random r = new Random();
        this.board = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = r.nextBoolean();
            }
        }
    }
        public void print() {
            for(int i=board.length-1;i>=0;i--){
                for(int j=board[0].length-1;j>=0;j--){
                    System.out.print(board[i][j]+" ");
                }
                System.out.println();
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





    }

