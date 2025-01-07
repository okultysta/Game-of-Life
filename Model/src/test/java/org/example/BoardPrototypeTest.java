package org.example;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BoardPrototypeTest {
    @Test
    public void boardPrototypeTest() {
        GameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(3,3,simulator);
        BoardPrototype prototype = null;
        try {
            prototype = new BoardPrototype(board);
            assertNotSame(board, prototype);
            assertNotSame(board, prototype.getInstance());
        } catch (BadCloneClass e) {
            throw new RuntimeException(e);
        }

    }
}
