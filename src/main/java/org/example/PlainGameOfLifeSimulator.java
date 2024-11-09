package org.example;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    public PlainGameOfLifeSimulator() {
    }

    public void doStep(GameOfLifeBoard board) {
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(0).size(); j++) {
                board.getBoard().get(i).get(j).updateState();
            }
        }

        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(0).size(); j++) {
                board.getBoard().get(i).get(j).nextState();
            }
        }
    }


}
