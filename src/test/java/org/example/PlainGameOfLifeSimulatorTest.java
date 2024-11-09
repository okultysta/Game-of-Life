package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlainGameOfLifeSimulatorTest {
    //Test checking if method doStep works properly
    @Test
    public void testDoStep() {
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 4, simulator);
        ArrayList<ArrayList<GameOfLifeCell>> initialBoard = board1.getBoard();
        board1.doSimulationStep();
        int alive;
        for (int i = 0; i < initialBoard.size(); i++) {
            for (int j = 0; j < initialBoard.get(i).size(); j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (initialBoard.get((k + initialBoard.size()) % initialBoard.size()).get((l + initialBoard.get(0).size()) %
                                initialBoard.get(0).size()).isAlive()) {
                            alive++;
                        }
                    }
                }
                if (initialBoard.get(i).get(j).isAlive()) {
                    switch (--alive) {
                        case 2, 3:
                            assertTrue(board1.getBoard().get(i).get(j).isAlive());
                            break;
                        default:
                            assertFalse(board1.getBoard().get(i).get(j).isAlive());
                            break;
                    }
                } else if (alive == 3) {
                    assertTrue(board1.getBoard().get(i).get(j).isAlive());
                }
            }
        }
    }
}
