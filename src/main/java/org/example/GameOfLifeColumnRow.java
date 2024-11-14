package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class GameOfLifeColumnRow {
    private ArrayList<GameOfLifeCell> cells;

    public GameOfLifeColumnRow() {
        cells = new ArrayList<GameOfLifeCell>();
    }

    public void addCell(GameOfLifeCell cell) {
        cells.add(cell);
    }

    public int countAlive() {
        int count = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public int countDead() {
        int count = 0;
        for (GameOfLifeCell cell : cells) {
            if (!cell.isAlive()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameOfLifeColumnRow that)) {
            return false;
        }
        return Objects.equals(cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cells);
    }

    @Override
    public String toString() {
        return "GameOfLifeColumnRow{countAlive=1, countDead=0, cells=" + cells.toString() + "}";
    }
}

