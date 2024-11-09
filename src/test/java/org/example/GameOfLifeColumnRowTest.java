package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeColumnRowTest {
    @Test
    public void GameOfLifeColumnRowTest() {
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

    @Test
    public void equalsAndHashCodeTest() {
        PlainGameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        GameOfLifeCell cell3 = new GameOfLifeCell(true);
        GameOfLifeColumnRow row = new GameOfLifeColumnRow();
        GameOfLifeColumnRow row2 = new GameOfLifeColumnRow();
        row.addCell(cell1);
        row.addCell(cell2);
        row.addCell(cell3);
        row2.addCell(cell1);
        row2.addCell(cell2);
        row2.addCell(cell3);
        assertEquals(row, row2);
        assertEquals(row, row);
        assertNotEquals(row, gameOfLifeSimulator);
        assertEquals(row.hashCode(), row2.hashCode());
    }
}
