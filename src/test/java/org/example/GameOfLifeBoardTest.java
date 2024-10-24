package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test checking if the board is initialized properly and with different content for each initlialization.
class GameOfLifeBoardTest {
/*
    @Test
    public void testBoardInitialization() {
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 4);
        GameOfLifeBoard board2 = new GameOfLifeBoard(3, 4);
        int same = 3 * 4;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board1.getBoard()[i][j] == board2.getBoard()[i][j])
                    same--;
            }
        }

        assertNotEquals(same, 0);
    }

    //Checking if the doStep() method correctly changes the layout of cells in the board.
    @Test
    public void doStepTest() {
        GameOfLifeBoard board1 = new GameOfLifeBoard(3, 3);
        boolean[][] initialBoard = board1.getBoard();
        board1.doStep();
        int alive;
        boolean[][] newBoard = board1.getBoard();
        for (int i = 1; i < initialBoard.length - 1; i++) {
            for (int j = 1; j < initialBoard[i].length - 1; j++) {
                alive = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (initialBoard[k][l]) {
                            alive++;
                        }
                    }
                }
                if (initialBoard[i][j]) {
                    switch (--alive) {
                        case 2, 3:
                            assertTrue(newBoard[i][j]);
                            break;
                        default:
                            assertFalse(newBoard[i][j]);
                            break;
                    }
                } else {
                    if (alive == 3)
                        assertTrue(newBoard[i][j]);
                }


            }
        }

    }

 */
}