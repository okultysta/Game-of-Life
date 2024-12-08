package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {
    @Test
    public void testGameOfLifeCell() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(false);
        assertTrue(cell.isAlive());
        assertFalse(cell2.isAlive());
    }

    @Test
    public void testAddingNeighborsAndCountingNestState() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell neighbour1 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour2 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour3 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour4 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour5 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour6 = new GameOfLifeCell(false);
        GameOfLifeCell neighbour7 = new GameOfLifeCell(false);
        GameOfLifeCell neighbour8 = new GameOfLifeCell(false);
        cell.addNeighbor(neighbour1);
        cell.addNeighbor(neighbour2);
        cell.addNeighbor(neighbour3);
        cell.addNeighbor(neighbour4);
        cell.addNeighbor(neighbour5);
        cell.addNeighbor(neighbour6);
        cell.addNeighbor(neighbour7);
        cell.addNeighbor(neighbour8);

        //Case cell alive and more than 3 neighbours are alive
        assertFalse(cell.nextState());
        //dodanie obserwatora
        //Case cell alive and exactly 3 neighbours are alive
        neighbour5.setCell(false);
        neighbour4.setCell(false);
        assertTrue(cell.nextState());

        //Case cell alive and exactly 2 neighbours are alive
        neighbour3.setCell(false);
        assertTrue(cell.nextState());

        //Case cell alive and less than 2 neighbours are alive
        neighbour2.setCell(false);
        assertFalse(cell.nextState());

        //Case cell dead and more than 3 neighbours are alive
        cell.setCell(false);
        neighbour2.setCell(true);
        neighbour3.setCell(true);
        neighbour4.setCell(true);
        assertFalse(cell.nextState());

        //Case cell dead and exactly 3 neighbours are alive
        neighbour4.setCell(false);
        assertTrue(cell.nextState());

        //Case cell dead and less than 3 neighbours are alive
        neighbour3.setCell(false);
        assertFalse(cell.nextState());
    }

    @Test
    public void testUpdateState() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell neighbour = new GameOfLifeCell(true);
        GameOfLifeCell neighbour2 = new GameOfLifeCell(false);
        cell.addNeighbor(neighbour);
        cell.addNeighbor(neighbour2);
        assertFalse(cell.nextState());
        cell.updateState();
        assertFalse(cell.isAlive());
    }

    @Test
    public void hashCodeTest() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(true);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);
        assertEquals(cell.hashCode(), cell2.hashCode());
        assertNotEquals(cell.hashCode(), cell3.hashCode());
    }

    @Test
    public void equalsTest() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = new GameOfLifeCell(true);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);
        PlainGameOfLifeSimulator simulator = new PlainGameOfLifeSimulator();
        assertEquals(cell, cell);
        assertEquals(cell, cell2);
        assertNotEquals(cell, cell3);
        assertNotEquals(cell, simulator);
    }

    @Test
    public void toStringTest() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell neighbour1 = new GameOfLifeCell(true);
        GameOfLifeCell neighbour2 = new GameOfLifeCell(true);
        cell.addNeighbor(neighbour1);
        cell.addNeighbor(neighbour2);
        assertTrue(cell.toString().contains(String.valueOf(cell.isAlive())));
        assertTrue(cell.toString().contains(String.valueOf(cell.nextState())));
    }

    @Test
    public void comparableTest() {
        GameOfLifeCell cellTrue1 = new GameOfLifeCell(true);
        GameOfLifeCell cellTrue2 = new GameOfLifeCell(true);
        GameOfLifeCell cellFalse1 = new GameOfLifeCell(false);
        GameOfLifeCell cellFalse2 = new GameOfLifeCell(false);

        GameOfLifeCell neighbourAlive = new GameOfLifeCell(true);
        GameOfLifeCell neighbourDead = new GameOfLifeCell(false);

        assertThrows(NullPointerException.class, () -> cellTrue1.compareTo(null));

        cellTrue1.addNeighbor(neighbourAlive);
        cellTrue1.addNeighbor(neighbourAlive);
        cellTrue1.addNeighbor(neighbourAlive);
        assertTrue(cellTrue1.nextState());

        cellTrue2.addNeighbor(neighbourDead);
        assertFalse(cellTrue2.nextState());

        cellFalse1.addNeighbor(neighbourAlive);
        cellFalse1.addNeighbor(neighbourAlive);
        cellFalse1.addNeighbor(neighbourAlive);
        assertTrue(cellFalse1.nextState());

        cellFalse2.addNeighbor(neighbourDead);
        assertFalse(cellFalse2.nextState());


        assertEquals(1, cellTrue1.compareTo(cellFalse1));
        assertEquals(-1, cellFalse1.compareTo(cellTrue1));

        assertEquals(1, cellTrue1.compareTo(cellTrue2));
        assertEquals(-1, cellTrue2.compareTo(cellTrue1));

        assertEquals(1, cellFalse1.compareTo(cellFalse2));
        assertEquals(-1, cellFalse2.compareTo(cellFalse1));

        cellTrue2.addNeighbor(neighbourAlive);
        cellTrue2.addNeighbor(neighbourAlive);
        assertTrue(cellTrue2.nextState());

        assertEquals(0, cellTrue2.compareTo(cellTrue1));

        cellFalse2.addNeighbor(neighbourAlive);
        cellFalse2.addNeighbor(neighbourAlive);
        cellFalse2.addNeighbor(neighbourAlive);
        assertTrue(cellFalse2.nextState());

        assertEquals(0, cellFalse2.compareTo(cellFalse1));
    }

    @Test
    public void cloneTest() {
        GameOfLifeCell cell = new GameOfLifeCell(true);
        GameOfLifeCell cell2 = cell.clone();
        assertEquals(cell, cell2);
        assertNotSame(cell, cell2);
    }

}
