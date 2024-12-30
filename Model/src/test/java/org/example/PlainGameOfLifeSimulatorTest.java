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
        GameOfLifeBoard board2 = new GameOfLifeBoard(3, 4, simulator, 30);
        GameOfLifeCell[][] initialBoard = board1.getBoard();
        board1.doSimulationStep();
        int alive;
        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard[i].length; j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (initialBoard[(k + initialBoard.length) % initialBoard.length][(l + initialBoard[0].length) %
                                initialBoard[0].length].isAlive()) {
                            alive++;
                        }
                    }
                }
                if (initialBoard[i][j].isAlive()) {
                    switch (--alive) {
                        case 2, 3:
                            assertTrue(board1.getBoard()[i][j].isAlive());
                            break;
                        default:
                            assertFalse(board1.getBoard()[i][j].isAlive());
                            break;
                    }
                } else if (alive == 3) {
                    assertTrue(board1.getBoard()[i][j].isAlive());
                }
            }
        }
    }
}
