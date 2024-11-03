package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeColumnRowTest {
    @Test
    public void testGameOfLifeColumnRow() {
        GameOfLifeColumnRow gameOfLifeColumnRow = new GameOfLifeColumnRow();
    }

    @Test
    public void testAddingCell() {
        GameOfLifeColumnRow Row = new GameOfLifeColumnRow();
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(true);
        Row.addCell(cell1);
        Row.addCell(cell2);
        Row.addCell(cell3);
        assertEquals(2, Row.countAlive());
        assertEquals(1, Row.countDead());
    }
}
