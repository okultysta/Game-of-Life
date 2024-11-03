package org.example;

import org.junit.jupiter.api.Test;

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
}
