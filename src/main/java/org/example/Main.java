package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            GameOfLifeBoard board = new GameOfLifeBoard(5,5);
            board.print();
            board.doStep();
            System.out.println();
            board.print();
        }
    }
