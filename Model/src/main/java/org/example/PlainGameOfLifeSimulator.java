package org.example;

import java.io.Serializable;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator, Serializable, Cloneable {

    public PlainGameOfLifeSimulator() {
    }

    public void doStep(GameOfLifeBoard board) {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                board.getBoard()[i][j].nextState();
            }
        }

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {

                board.getBoard()[i][j].updateState();
            }
        }
    }


    @Override
    public PlainGameOfLifeSimulator clone() {
        try {
            return (PlainGameOfLifeSimulator) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
