package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameOfLifeBoardTest {
    //Test checking if the board is initialized properly and with different content for each initlialization.
    @Test
    public void boardInitializationTest() {
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 4, simulator);
        GameOfLifeBoard board2 = new GameOfLifeBoard(3, 4, simulator);
        int same = 3 * 4;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board1.getBoard()[i][j] == board2.getBoard()[i][j])
                    same--;
            }
        }
        assertNotEquals(same, 0);
    }

    //Test checking getter and setter
    @Test
    public void setCellTest() {
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 4, simulator);
        board1.setCell(1, 1, false);
        assertFalse(board1.getBoard()[1][1].isAlive());
        board1.setCell(1, 1, true);
        assertTrue(board1.getBoard()[1][1].isAlive());
    }
}