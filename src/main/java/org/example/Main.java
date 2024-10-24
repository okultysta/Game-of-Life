package org.example;

//Autorzy: Kacper Maziarz 251586, Jedrzej Bartoszewski 251482
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(4, 4, simulator);
        board.print();
        board.doSimulationStep();
        System.out.println();
        board.print();

    }
}
